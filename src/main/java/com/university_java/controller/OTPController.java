package com.university_java.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.university_java.service.EmailService;
import com.university_java.service.OtpService;

@RestController
@RequestMapping("/otp")
public class OTPController {
	
	@Autowired
    private OtpService otpService;

    @Autowired
    private EmailService emailService;
    
    private static String generatedOtp;
    private static String userEmail;

    @GetMapping("/verify-account")
    public String verifyOTP(@RequestParam String otp, @RequestParam String email) {
        
        if (generatedOtp != null && generatedOtp.equals(otp) && userEmail.equals(email)) {
            return "OTP verified successfully!";
        }
        return "Invalid OTP!";
    }

    @PostMapping("/send")
    public String sendOTP(@RequestParam String email) {
        
        String otp = generateOTP();
        generatedOtp = otp;  
        userEmail = email;  

        emailService.sendOTPEmail(email, otp);

        return "OTP sent to email: " + email;
    }

    private String generateOTP() {
        Random rand = new Random();
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            otp.append(rand.nextInt(10));  
        }
        return otp.toString();
    }

}
