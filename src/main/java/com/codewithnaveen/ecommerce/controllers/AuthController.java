package com.codewithnaveen.ecommerce.controllers;

import com.codewithnaveen.ecommerce.dtos.JwtResponse;
import com.codewithnaveen.ecommerce.dtos.UserDto;
import com.codewithnaveen.ecommerce.dtos.UserLoginRequest;
import com.codewithnaveen.ecommerce.mappers.UserMapper;
import com.codewithnaveen.ecommerce.repositories.UserRepository;
import com.codewithnaveen.ecommerce.services.JwtService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private UserRepository userRepository;
    private UserMapper userMapper;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> loginRequest(
            @Valid @RequestBody UserLoginRequest request
            ){

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var token = jwtService.generateToken(request.getEmail());

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/validate")
    public Boolean validate(@RequestHeader("Authorization") String authHeader){

        System.out.println("Validate Controller called");
        var token = authHeader.replace("Bearer ", "");
        return jwtService.validateToken(token);
    }
    
    @GetMapping("/me")
    public ResponseEntity<UserDto> me(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var email = (String) authentication.getPrincipal();
        
        var user = userRepository.findByEmail(email).orElse(null);
        if(user == null){
            return ResponseEntity.notFound().build();
        }

        var userDto = userMapper.toDto(user);

        return ResponseEntity.ok(userDto);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Void> handleBadCredentialExceptions(){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
