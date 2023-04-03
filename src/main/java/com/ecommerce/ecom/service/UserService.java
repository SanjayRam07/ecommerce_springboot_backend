package com.ecommerce.ecom.service;

import org.springframework.stereotype.Service;

import com.ecommerce.ecom.dto.ResponseDto;
import com.ecommerce.ecom.dto.user.SignupDto;

@Service
public class UserService {

    public ResponseDto signUp(SignupDto signupDto) {
        return new ResponseDto("success", "dummy response");
    }
    
}
