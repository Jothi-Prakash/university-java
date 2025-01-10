package com.university_java.service;

import org.springframework.stereotype.Service;


import org.springframework.stereotype.Service;
import java.util.Random;

@Service
public class OtpService {

	    public String generateOTP() {
	        Random rand = new Random();
	        StringBuilder otp = new StringBuilder();
	        for (int i = 0; i < 6; i++) {
	            otp.append(rand.nextInt(10));
	        }
	        return otp.toString();
	    }
	 
}
