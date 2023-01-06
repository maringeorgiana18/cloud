package com.backend.model.user;

import lombok.*;

import javax.validation.constraints.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class UpdateUserModel {

    @NotNull(message = "Id is invalid")
    private Integer id;

    @NotBlank(message = "User name is invalid")
    private String userName;

    private String password;

    @NotBlank(message = "Email is invalid")
    @Email(message = "Email is invalid")
    private String email;

}
