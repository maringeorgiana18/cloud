package com.backend.dao.audit;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;

@Document
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AuditModel {
    private String auditType;
    private Integer userId;
    private Timestamp createDate;
}
