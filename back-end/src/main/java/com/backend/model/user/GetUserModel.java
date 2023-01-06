package com.backend.model.user;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class GetUserModel {

    private Integer id;

    private String userName;

    private String email;

    private String role;

    private boolean isEnable;

}
