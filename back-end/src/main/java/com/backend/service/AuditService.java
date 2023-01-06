package com.backend.service;

import com.backend.dao.audit.AuditModel;
import com.backend.dao.audit.AuditRepository;
import com.backend.security.entities.User;
import com.backend.type.AuditType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class AuditService {

    @Autowired
    private AuditRepository auditRepository;

    public void createLoginAudit(User user) {
        AuditModel auditModel = new AuditModel();
        auditModel.setAuditType(AuditType.LOGIN.name());
        auditModel.setUserId(user.getId());
        auditModel.setCreateDate(new Timestamp(System.currentTimeMillis()));
        auditRepository.save(auditModel);
    }

    public void createNewUserAudit(User user) {
        AuditModel auditModel = new AuditModel();
        auditModel.setAuditType(AuditType.NEW_USER.name());
        auditModel.setUserId(user.getId());
        auditModel.setCreateDate(new Timestamp(System.currentTimeMillis()));
        auditRepository.save(auditModel);
    }

}
