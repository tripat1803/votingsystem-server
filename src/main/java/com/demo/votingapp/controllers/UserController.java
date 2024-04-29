package com.demo.votingapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.votingapp.controllers.requests.LoginUser;
import com.demo.votingapp.controllers.requests.RegisterUser;
import com.demo.votingapp.controllers.responses.AuthenticatedResponse;
import com.demo.votingapp.models.User;
import com.demo.votingapp.repositories.UserRepository;
import com.demo.votingapp.service.JwtService;
import com.demo.votingapp.utils.Role;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {"*"})
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterUser request) {
        User user = new User();
        user.setEmail(request.email);
        user.setAdhaarCard(request.adhaarCard);
        user.setName(request.name);
        user.setPassword(passwordEncoder.encode(request.password));
        user.setRole(Role.USER);
        User savedUser = this.userRepository.save(user);

        String _at = this.jwtService.generateAccessToken(savedUser);

        return ResponseEntity.ok(new AuthenticatedResponse(_at));
    }

    @PostMapping("")
    public ResponseEntity<?> loginUser(@RequestBody LoginUser request) {
        this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email, request.password));
        User user = this.userRepository.findFirstByEmail(request.email);

        String _at = this.jwtService.generateAccessToken(user);

        return ResponseEntity.ok(new AuthenticatedResponse(_at));
    }
}
