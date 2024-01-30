package com.lsm.backend.controller;
import com.lsm.backend.exception.BadRequestException;
import com.lsm.backend.model.AuthProvider;
import com.lsm.backend.model.User;
import com.lsm.backend.payload.ApiResponse;
import com.lsm.backend.payload.AuthResponse;
import com.lsm.backend.payload.LoginRequest;
import com.lsm.backend.payload.SignUpRequest;
import com.lsm.backend.repository.UserRepository;
import com.lsm.backend.security.TokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenProvider tokenProvider;
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.createToken(authentication);
        return ResponseEntity.ok(new AuthResponse(token));
    }
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new BadRequestException("Email address already in use.");
        }
        // Creating user's account
        User user = new User();
        user.setName(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(signUpRequest.getPassword());
        user.setAuthProvider(AuthProvider.local);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User result = userRepository.save(user);
        //회원가입 성공시 user/me
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/me")
                .buildAndExpand(result.getId()).toUri();
        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "User registered successfully@"));
    }
    @PostMapping("/local/login")
    public  ResponseEntity<?> loginLocalUser(@Valid @RequestBody LoginRequest loginRequest){
        try {
            String email = loginRequest.getEmail();
            String password = loginRequest.getPassword();

            Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
            //인증처리
            Authentication authenticate = authenticationManager.authenticate(authentication);
            SecurityContextHolder.getContext().setAuthentication(authenticate);

            String token = tokenProvider.createToken(authenticate);

            return ResponseEntity.ok(new AuthResponse(token));
        } catch (AuthenticationException e) {
            // 인증실패
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        } catch (Exception e) {
            // 나머지
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> localRegisterUser(@Valid @RequestBody SignUpRequest signupRequest) {

        // 비즈니스 로직
        String name = signupRequest.getName();
        String email = signupRequest.getEmail();
        String password = signupRequest.getPassword();

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        // 비밀번호 암호화
        user.setPassword(bCryptPasswordEncoder.encode(password));

        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully!");

    }


}