package com.backend.service;

import com.backend.model.Message;
import com.backend.model.user.AddUserModel;
import com.backend.model.user.UpdateUserModel;
import com.backend.mapper.UserMapper;
import com.backend.rabbit.Email;
import com.backend.rabbit.RabbitMqSender;
import com.backend.security.entities.User;
import com.backend.security.repositories.UserRepository;
import com.backend.type.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RabbitMqSender rabbitMqSender;

    public ResponseEntity<?> add(String type, AddUserModel addUserModel) {
        User user = userMapper.addUserModelToUser(addUserModel);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(type);
        user.setEnable(true);
        userRepository.save(user);

        Email email = new Email();
        email.setEmail(addUserModel.getEmail());
        rabbitMqSender.send(email);
        return ResponseEntity.ok(new Message("Register completed"));
    }

    public ResponseEntity<?> getAll(String userType) {
        List<User> users = userRepository.findAllByRole(userType);
        return ResponseEntity.ok(userMapper.usersToGetUserModels(users));
    }

    public ResponseEntity<?> getInfo() {
        User user = userRepository.findByEmail(getCurrentUser().getEmail()).get();
        return ResponseEntity.ok(userMapper.userToGetUserModel(user));
    }

    public ResponseEntity<?> update(UpdateUserModel updateUserModel) {
        User user = userMapper.updateUserModelMapperToUser(updateUserModel);
        if (user.getPassword() != null && !Pattern.matches("(?=^.{8,}$)(?=.*\\d)(?=.*[!@#$%^&*]+)(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$", user.getPassword())) {
            return ResponseEntity.badRequest().body(new Message("Password is invalid"));
        }
        updateUserFields(user);
        return ResponseEntity.ok(new Message("User updated"));
    }

    public ResponseEntity<?> reactivateTeacher(String email) {
        User teacher = userRepository.findByEmail(email).get();
        teacher.setEnable(true);
        userRepository.save(teacher);
        return ResponseEntity.ok(new Message("Teacher reactivated"));
    }

    public ResponseEntity<?> delete(String email) {
        User user = userRepository.findByEmail(email).get();
        if (user.getRole().equals(UserType.TEACHER.name())) {
            user.setEnable(false);
            userRepository.save(user);
        } else {
            userRepository.deleteByEmail(email);
        }
        return ResponseEntity.ok(new Message("User deleted"));
    }

    public ResponseEntity<?> checkEmailExist(String email) {
        return ResponseEntity.ok(userRepository.existsByEmail(email));
    }

    public ResponseEntity<?> checkEmailForUpdate(String email, Integer id) {
        if (userRepository.existsByEmail(email)) {
            User user = userRepository.findByEmail(email).get();
            if (!user.getId().equals(id)) {
                return ResponseEntity.ok(false);
            }
        }
        return ResponseEntity.ok(true);
    }

    public User getUser(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        user.orElseThrow(() -> new UsernameNotFoundException("Not found : " + email));
        return user.get();
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return getUser(authentication.getName());
        }
        return null;
    }

    private void updateUserFields(User user) {
        User userDTO = userRepository.findById(user.getId()).get();
        userDTO.setUserName(user.getUserName());
        userDTO.setEmail(user.getEmail());
        if(user.getPassword() != null) {
            userDTO.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userRepository.save(userDTO);
    }

}
