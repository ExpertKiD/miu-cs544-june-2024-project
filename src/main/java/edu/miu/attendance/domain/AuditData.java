package edu.miu.attendance.domain;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.time.LocalDateTime;

@Embeddable
@Data
public class AuditData {
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private LocalDateTime updatedBy;
    private LocalDateTime createdBy;

}
