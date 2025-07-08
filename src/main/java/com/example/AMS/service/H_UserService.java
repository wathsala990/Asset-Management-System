package com.example.AMS.service;

import com.example.AMS.model.User;
import com.example.AMS.repository.H_UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class H_UserService {
    @Autowired
    private H_UserRepository userRepository;

   // @Autowired
   // private PasswordEncoder passwordEncoder;

    @Autowired
    private H_AssetService assetService;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(String userId) {
        return userRepository.findById(userId).orElse(null);
    }

//    public User saveUser(User user) {
//        // Encode password before saving
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        return userRepository.save(user);
//    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    public List<User> getUsersByRole(String role) {
        return userRepository.findByRole(role);
    }

    public List<User> getUsersByAsset(String assetId) {
        return userRepository.findByAsset_AssetID(assetId);
    }

    public boolean usernameExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    public boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}