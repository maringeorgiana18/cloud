package com.backend.model.user;

import lombok.*;

import javax.validation.constraints.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class AddUserModel {

    @NotBlank(message = "User name is invalid")
    private String userName;

    @NotBlank(message = "Password is invalid")
    @Pattern(regexp = "(?=^.{8,}$)(?=.*\\d)(?=.*[!@#$%^&*]+)(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$", message = "Password is invalid")
    private String password;

    @NotBlank(message = "Email is invalid")
    @Email(message = "Email is invalid")
    private String email;

}
