package com.backend.model.open;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AuthenticationRequest implements Serializable {

    @NotBlank(message = "Email is invalid")
    @Email(message = "Email is invalid")
    private String email;

    @NotBlank(message = "Password is invalid")
    private String password;
}