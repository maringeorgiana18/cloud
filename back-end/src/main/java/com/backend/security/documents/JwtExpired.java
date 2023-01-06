package com.backend.security.documents;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Document
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class JwtExpired {

    @Id
    private String id;
    private String jwt;
}
