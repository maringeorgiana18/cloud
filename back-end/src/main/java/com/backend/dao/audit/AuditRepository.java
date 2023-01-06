package com.backend.dao.audit;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditRepository extends MongoRepository<AuditModel, Integer> {
    Integer countAllByAuditType(String auditType);
}
