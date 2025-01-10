package com.university_java.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
	
	@Autowired
    private JavaMailSender mailSender;

	public void sendOTPEmail(String recipientEmail, String otp) {
	    try {
	        MimeMessage message = mailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message, true);
	        helper.setTo(recipientEmail);
	        helper.setSubject("OTP Verification");
	        
	        // Combine OTP display and verification link
	        String emailContent = "<div><h3>Your OTP is: " + otp + "</h3></div>" +
	                              "<div><br>To verify your account, click the link below:</div>" +
	                              "<div><a href=\"http://localhost:9000/otp/verify-account?otp=" + otp + "&email=" + recipientEmail + "\" target=\"_blank\">Click to verify OTP</a></div>" +
	                              "<div>If you did not request an OTP, please ignore this message.</div>";

	        helper.setText(emailContent, true);
	        mailSender.send(message);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

}
