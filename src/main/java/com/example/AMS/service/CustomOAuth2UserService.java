package com.example.AMS.service;

import com.example.AMS.model.Role;
import com.example.AMS.model.User; // <-- Add this import
import com.example.AMS.repository.RoleRepository;
import com.example.AMS.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Value("${app.security.admin-emails:}")
    private String adminEmailsString;

    @Value("${app.security.director-emails:}")
    private String directorEmailsString;

    public CustomOAuth2UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    private Set<String> getEmailSet(String emailString) {
        if (emailString == null || emailString.trim().isEmpty()) {
            return Collections.emptySet();
        }
        return Arrays.stream(emailString.split(","))
                     .map(String::trim)
                     .map(String::toLowerCase)
                     .collect(Collectors.toSet());
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");

        if (email == null || email.isEmpty()) {
            throw new OAuth2AuthenticationException("Email not found from OAuth2 provider");
        }

        String userEmailLowerCase = email.toLowerCase();

        User user = userRepository.findByEmail(userEmailLowerCase)
            .orElseGet(() -> {
                User newUser = new User();
                newUser.setEmail(userEmailLowerCase);
                newUser.setFullName(name);
                newUser.setUsername(generateUniqueUsername(userEmailLowerCase));
                newUser.setEnabled(true);
                newUser.setPassword("oauth2_user_no_password");

                Set<Role> assignedRoles = new HashSet<>();
                Set<String> adminEmails = getEmailSet(adminEmailsString);
                Set<String> directorEmails = getEmailSet(directorEmailsString);

                if (adminEmails.contains(userEmailLowerCase)) {
                    Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                            .orElseThrow(() -> new RuntimeException("Error: Role 'ROLE_ADMIN' not found."));
                    assignedRoles.add(adminRole);
                } else if (directorEmails.contains(userEmailLowerCase)) {
                    Role directorRole = roleRepository.findByName("ROLE_DIRECTOR")
                            .orElseThrow(() -> new RuntimeException("Error: Role 'ROLE_DIRECTOR' not found."));
                    assignedRoles.add(directorRole);
                } else {
                    Role userRole = roleRepository.findByName("ROLE_USER")
                            .orElseThrow(() -> new RuntimeException("Error: Role 'ROLE_USER' not found."));
                    assignedRoles.add(userRole);
                }
                newUser.setRoles(assignedRoles);

                return userRepository.save(newUser);
            });

        Set<GrantedAuthority> authorities = user.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority(role.getName()))
            .collect(Collectors.toSet());

        return new DefaultOAuth2User(
            authorities,
            attributes,
            "email"
        );
    }

    private String generateUniqueUsername(String email) {
        String baseUsername = email.split("@")[0].replaceAll("[^a-zA-Z0-9]", "");
        if (baseUsername.isEmpty()) {
            baseUsername = "user";
        }

        String username = baseUsername;
        int counter = 1;

        while (userRepository.existsByUsername(username)) {
            username = baseUsername + "_" + counter;
            counter++;
        }
        return username;
    }
}