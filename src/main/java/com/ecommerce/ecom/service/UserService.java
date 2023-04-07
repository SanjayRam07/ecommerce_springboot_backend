package com.ecommerce.ecom.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;

import javax.transaction.Transactional;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.ecom.dto.ResponseDto;
import com.ecommerce.ecom.dto.user.SigninDto;
import com.ecommerce.ecom.dto.user.SigninResponseDto;
import com.ecommerce.ecom.dto.user.SignupDto;
import com.ecommerce.ecom.exceptions.AuthenticationFailException;
import com.ecommerce.ecom.exceptions.CustomException;
import com.ecommerce.ecom.model.AuthenticationToken;
import com.ecommerce.ecom.model.User;
import com.ecommerce.ecom.repository.UserRepo;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    AuthenticationService authenticationService;

    @Transactional
    public ResponseDto signUp(SignupDto signupDto) {
        User newUser=new User();

        // check if user exists
        if(Objects.nonNull( userRepo.findByEmail(signupDto.getEmail()) )) {
            // user exists - throw exception
            throw new CustomException("User Already Exists");
        }

        // hash password
        String encPwd=null;
        try {
            encPwd=hashPassword(signupDto.getPassword());
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        // save user
        newUser.setFirstName(signupDto.getFirstName());
        newUser.setLastName(signupDto.getLastName());
        newUser.setEmail(signupDto.getEmail());
        newUser.setPassword(encPwd);

        userRepo.save(newUser);

        // create token
        final AuthenticationToken authenticationToken=new AuthenticationToken(newUser);
        authenticationService.saveConfirmationToken(authenticationToken);
        
        return new ResponseDto("success", "dummy response");
    }
    
    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md=MessageDigest.getInstance("MD5");

        md.update(password.getBytes());
        byte[] digest=md.digest();
        String hash=DatatypeConverter.printHexBinary(digest).toUpperCase();

        return hash;
    }

    public SigninResponseDto signIn(SigninDto signinDto) {
        
        // find user by email
        User user=userRepo.findByEmail(signinDto.getEmail());

        if(Objects.isNull(user)) {
            throw new AuthenticationFailException("Invalid User");
        }

        // hash password
        //compare with DB
        try {
            if( !user.getPassword().equals(hashPassword(signinDto.getPassword())) ) {
                throw new AuthenticationFailException("invalid password");
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        // if match - retrieve token
        AuthenticationToken authenticationToken = authenticationService.getToken(user);

        if( Objects.isNull(authenticationToken) ) {
            throw new CustomException("token is not present");
        }

        // return response
        return new SigninResponseDto("success", authenticationToken.getToken());

    }

    public List<User> listUsers() {
        return userRepo.findAll();
    }
}
