package com.cybertek.orm.cinemaapp.controller;

import com.cybertek.orm.cinemaapp.model.User;
import com.cybertek.orm.cinemaapp.model.jwt.AuthenticationRequest;
import com.cybertek.orm.cinemaapp.model.jwt.AuthenticationResponse;
import com.cybertek.orm.cinemaapp.service.UserService;
import com.cybertek.orm.cinemaapp.util.JwtUtil;
import com.cybertek.orm.cinemaapp.util.Response;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticateController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthenticateController(AuthenticationManager authenticationManager, UserService userService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello World";
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Response> authenticate(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        String password = authenticationRequest.getPassword();
        String username = authenticationRequest.getUsername();
        try {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, password);
            authenticationManager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Bad Credentials!");
        }

        String jwtToken = jwtUtil.generateToken(userService.readByUsername(username), username);
        Response response = Response.builder().success(true).message("Login Successfully!").data(new AuthenticationResponse(jwtToken)).code(200).build();
        return ResponseEntity.ok(response);

    }

    @PostMapping("/create-user")
    public ResponseEntity<Response> createUser(@RequestBody User user) throws Exception {
        User userCreated = userService.create(user);
        try {
            Response response = Response.builder()
                    .success(true)
                    .code(200)
                    .message("User has been created successfully!")
                    .data(userCreated)
                    .build();
            return ResponseEntity.ok(response);
        }catch (Exception e) {
           throw new Exception("Failed to create user!");
        }

    }
}
