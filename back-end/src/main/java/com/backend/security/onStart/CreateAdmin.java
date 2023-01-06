package com.backend.security.onStart;

import com.backend.type.UserType;
import com.backend.security.entities.User;
import com.backend.security.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class CreateAdmin {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void createAdmin() {
        if (!userRepository.existsByRole(UserType.ADMIN.name())) {
            User admin = new User();
            admin.setRole(UserType.ADMIN.name());
            admin.setUserName("admin");
            admin.setEmail("admin@gmail.com");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setEnable(true);
            userRepository.save(admin);
        }
    }

}
