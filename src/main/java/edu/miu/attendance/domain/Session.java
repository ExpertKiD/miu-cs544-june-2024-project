package edu.miu.attendance.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate sessionDate;

    private LocalTime startTime;

    private LocalTime endTime;

    @Embedded
    private AuditData auditData;

    @PrePersist
    void onCreate() {
        if (auditData == null) {
            auditData = new AuditData();
        }
    }
}
