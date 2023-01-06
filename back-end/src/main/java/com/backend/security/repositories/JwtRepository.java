package com.backend.security.repositories;

import com.backend.security.documents.JwtExpired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JwtRepository extends MongoRepository<JwtExpired, String> {

    Boolean existsJwtExpiredByJwt(String jwt);
}
