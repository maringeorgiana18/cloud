package com.backend.controller;

import com.backend.type.UserType;
import com.backend.validator.valueOfEnum.ValueOfEnum;
import com.backend.model.user.AddUserModel;
import com.backend.model.user.UpdateUserModel;
import com.backend.validator.user.add.ValidateAddUserModel;
import com.backend.validator.user.delete.ValidateDeleteUser;
import com.backend.validator.user.reactivateTeacher.ValidateReactivateTeacher;
import com.backend.validator.user.update.ValidateUpdateUserModel;
import com.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/{type}")
    public ResponseEntity<?> add(@PathVariable @ValueOfEnum(enumClass = UserType.class, message = "Type is invalid") String type, @RequestBody @Valid @ValidateAddUserModel AddUserModel user){
        return userService.add(type, user);
    }

    @GetMapping("/{type}")
    public ResponseEntity<?> getAll(@PathVariable @ValueOfEnum(enumClass = UserType.class, message = "Type is invalid") String type) {
        return userService.getAll(type);
    }

    @GetMapping("/info")
    public ResponseEntity<?> getInfo() {
        return userService.getInfo();
    }

    @GetMapping("/check-email/{email}/{id}")
    public ResponseEntity<?> checkEmail(@PathVariable String email, @PathVariable Integer id){
        return userService.checkEmailForUpdate(email, id);
    }

    @GetMapping("/check-email/{email}")
    public ResponseEntity<?> checkEmail(@PathVariable String email){
        return userService.checkEmailExist(email);
    }

    @PutMapping("/reactivate-teacher/{email}")
    public ResponseEntity<?> reactivateTeacher(@PathVariable @ValidateReactivateTeacher String email) {
        return userService.reactivateTeacher(email);
    }

    @PutMapping("")
    public ResponseEntity<?> update(@RequestBody @Valid @ValidateUpdateUserModel UpdateUserModel user){
        return userService.update(user);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<?> delete(@PathVariable @ValidateDeleteUser String email) {
        return userService.delete(email);
    }

}
