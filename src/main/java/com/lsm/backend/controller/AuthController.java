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
import com.lsm.backend.security.UserPrincipal;
import com.lsm.backend.security.oauth2.OAuth2AuthenticationSuccessHandler;
import com.lsm.backend.util.AuthenticationSuccessHandlerUtil;
import com.lsm.backend.util.JwtTokenUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
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
import org.springframework.security.web.RedirectStrategy;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
@RestController  //Controller처럼 동작하면서 return값을 response body에 입력가능
@RequiredArgsConstructor // Lombok에서 @AutoWired 대체하는 어노테이션
public class AuthController {
    private final RedirectStrategy redirectStrategy;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final TokenProvider tokenProvider;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationSuccessHandlerUtil successHandler;
    //@Valid는 LoginRequest 객체 검증

    private String determineRedirectUrl(HttpServletRequest request, Authentication authentication) {

        String redirectUrl = "http://localhost:3000/local/redirect";

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(redirectUrl);

        Long userId = ((UserPrincipal)authentication.getPrincipal()).getId();

        String token = jwtTokenUtil.generateToken(userId);

        builder.queryParam("token", token);

        return builder.toUriString();
    }

    @PostMapping("/signIn")
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
    @PostMapping("/signUp")
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

        userRepository.save(user);

        // 회원 가입 성공 API 리턴
        return ResponseEntity.ok(new ApiResponse(true, "User registered successfully"));

    }
}