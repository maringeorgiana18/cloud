package com.backend.model.open;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AuthenticationResponse implements Serializable {
    private String token;
    private String role;
}