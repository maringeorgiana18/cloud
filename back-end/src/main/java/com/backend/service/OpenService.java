package com.backend.service;

import com.backend.model.Message;
import com.backend.model.open.AuthenticationRequest;
import com.backend.model.open.AuthenticationResponse;
import com.backend.security.services.JwtService;
import com.backend.security.services.JwtUtil;
import com.backend.security.services.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class OpenService {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuditService auditService;

    public ResponseEntity<?> createAuthenticationToken(AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword())
            );
        }
        catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body("Incorrect username or password");
        }

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getEmail());

        final String token = jwtTokenUtil.generateToken(userDetails);

        auditService.createLoginAudit(userService.getUser(authenticationRequest.getEmail()));
        return ResponseEntity.ok(new AuthenticationResponse(token, userDetails.getAuthorities().toArray()[0].toString()));
    }

    public ResponseEntity<?> logout(String jwt){
        jwtService.addToBlackList(jwt);
        return ResponseEntity.ok(new Message("You are logout"));
    }

}
