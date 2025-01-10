//package com.university_java.service;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//@Service
//public class CaptchaService {	
//	
//	@Value("${google.recaptcha.secretKey}")
//    private String secretKey;
//
//    public boolean verifyCaptcha(String captchaResponse) {
//        String url = "https://www.google.com/recaptcha/api/siteverify?secret=" + secretKey + "&response=" + captchaResponse;
//
//        // Send a POST request to Google reCAPTCHA API to verify the CAPTCHA response
//        RestTemplate restTemplate = new RestTemplate();
//        CaptchaResponse captchaApiResponse = restTemplate.postForObject(url, null, CaptchaResponse.class);
//
//        // Return whether CAPTCHA verification was successful
//        return captchaApiResponse != null && captchaApiResponse.isSuccess();
//    }
//
//    // Inner class for parsing the response from Google reCAPTCHA API
//    private static class CaptchaResponse {
//        private boolean success;
//
//        public boolean isSuccess() {
//            return success;
//        }
//
//        public void setSuccess(boolean success) {
//            this.success = success;
//        }
//    }
//
//}
