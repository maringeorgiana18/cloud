package com.backend.security.services;

import com.backend.security.documents.JwtExpired;
import com.backend.security.repositories.JwtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    @Autowired
    private JwtRepository jwtRepository;

    public Boolean checkBlackList(String jwt){
        return jwtRepository.existsJwtExpiredByJwt(jwt);
    }

    public void addToBlackList(String jwt){
        JwtExpired jwtExpired = new JwtExpired();
        jwtExpired.setJwt(jwt);
        jwtRepository.save(jwtExpired);
    }

}
