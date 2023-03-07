package com.onurcansever.blog.service.impl;

import com.onurcansever.blog.entity.Role;
import com.onurcansever.blog.entity.User;
import com.onurcansever.blog.exception.BlogApiException;
import com.onurcansever.blog.payload.LoginDto;
import com.onurcansever.blog.payload.RegisterDto;
import com.onurcansever.blog.repository.RoleRepository;
import com.onurcansever.blog.repository.UserRepository;
import com.onurcansever.blog.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String login(LoginDto loginDto) {

        Authentication authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return String.format("Logged in as %s", loginDto.getUsernameOrEmail());
    }

    @Override
    public String register(RegisterDto registerDto) {

        // Check if the username is already taken
        if (this.userRepository.existsByUsername(registerDto.getUsername())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Username is already taken");
        }

        // Check if the email is already taken
        if (this.userRepository.existsByEmail(registerDto.getEmail())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Email is already taken");
        }

        // Create a new user
        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(this.passwordEncoder.encode(registerDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = this.roleRepository.findByName("ROLE_USER").orElseThrow(() -> new BlogApiException(HttpStatus.INTERNAL_SERVER_ERROR, "User role not found"));
        roles.add(userRole);
        user.setRoles(roles);

        this.userRepository.save(user);

        return String.format("User %s registered successfully", registerDto.getUsername());
    }
}
