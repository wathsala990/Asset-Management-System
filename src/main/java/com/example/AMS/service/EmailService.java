package com.example.AMS.service;

public interface EmailService {
    void sendEmail(String to, String subject, String text);
    void sendVerificationEmail(User user, String verificationUrl);
    void sendPasswordResetEmail(String email, String resetLink);
}