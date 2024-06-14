package edu.miu.attendance.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "CourseOffering")
@Data
public class CourseOffering {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "credits")
    private double credits;

    @Enumerated(EnumType.STRING)
    @Column(name = "CourseOfferingType")
    private CourseOfferingType courseOfferingType;

    @Column(name = "capacity")
    private int capacity;

    @Column(name = "Room")
    private String room;

    @Embedded
    private AuditData auditData;
}
