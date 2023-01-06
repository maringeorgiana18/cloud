package com.backend.controller;

import com.backend.model.open.AuthenticationRequest;
import com.backend.service.OpenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@RestController
public class OpenController {

    @Autowired
    private OpenService openService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody @Valid AuthenticationRequest authenticationRequest) {
        return openService.createAuthenticationToken(authenticationRequest);
    }

    @PostMapping("/logout/{jwt}")
    public ResponseEntity<?> logout(@PathVariable String jwt){
        return openService.logout(jwt);
    }

}
