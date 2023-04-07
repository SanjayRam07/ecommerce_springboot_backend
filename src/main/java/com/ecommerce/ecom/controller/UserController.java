package com.ecommerce.ecom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecom.dto.ResponseDto;
import com.ecommerce.ecom.dto.user.SigninDto;
import com.ecommerce.ecom.dto.user.SigninResponseDto;
import com.ecommerce.ecom.dto.user.SignupDto;
import com.ecommerce.ecom.model.User;
import com.ecommerce.ecom.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    // signup
    @PostMapping("/signup")
    public ResponseDto signup(@RequestBody SignupDto signupDto) {
        return userService.signUp(signupDto);
    }

    // signin
    @PostMapping("/signin")
    public SigninResponseDto signin(@RequestBody SigninDto signinDto) {
        return userService.signIn(signinDto);
    }

    // list users
    @GetMapping("/listUsers")
    public List<User> listUsers() {
        return userService.listUsers();
    }
}
