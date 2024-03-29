package com.lsm.backend.controller;

import com.lsm.backend.exception.ResourceNotFoundException;
import com.lsm.backend.model.User;
import com.lsm.backend.repository.UserRepository;
import com.lsm.backend.security.UserPrincipal;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lsm.backend.security.CurrentUser;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }
}