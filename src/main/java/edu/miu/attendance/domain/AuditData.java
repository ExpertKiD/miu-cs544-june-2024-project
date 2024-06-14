package edu.miu.attendance.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.time.LocalDateTime;

@Embeddable
@Data
public class AuditData {

    @Column(insertable = false, updatable = false)
    private LocalDateTime createdOn;
    @Column(insertable = false, updatable = false)
    private LocalDateTime updatedOn;
    @Column(insertable = false, updatable = false)
    private LocalDateTime updatedBy;
    @Column(insertable = false, updatable = false)
    private LocalDateTime createdBy;

}
