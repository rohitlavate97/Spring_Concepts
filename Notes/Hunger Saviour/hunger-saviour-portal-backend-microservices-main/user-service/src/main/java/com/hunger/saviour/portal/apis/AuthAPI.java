package com.hunger.saviour.portal.apis;

import com.hunger.saviour.portal.dtos.AuthRequest;
import com.hunger.saviour.portal.dtos.ResponseDTO;
import com.hunger.saviour.portal.dtos.SignUpRequest;
import com.hunger.saviour.portal.entities.UserEntity;
import com.hunger.saviour.portal.services.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("users")
@Slf4j
public class AuthAPI {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/signup")
    public void registerUser(@RequestBody SignUpRequest signUpRequest){

    }

    @PostMapping("/login")
    public ResponseDTO authenticateUser(@RequestBody AuthRequest authRequest){
        Authentication authentication = this.authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserEntity user =(UserEntity) authentication.getPrincipal();
        return ResponseDTO.builder()
                .token(this.authenticationService.generateToken(authentication))
                .username(user.getUsername())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @GetMapping("/validate")
    public Boolean validateToken(@RequestParam("token") String token) throws Exception {
        log.info("Entered validate token method at "+ LocalDateTime.now());
        return authenticationService.validateToken(token);
    }
}
