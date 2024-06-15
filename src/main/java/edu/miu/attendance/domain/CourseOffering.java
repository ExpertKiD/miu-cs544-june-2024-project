package edu.miu.attendance.domain;

import edu.miu.attendance.enumType.CourseOfferingType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

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

    @ManyToOne
    private Course course;

    @ManyToOne
    private Faculty faculty;

    @OneToMany
    private List<Session> sessions;

    @Embedded
    private AuditData auditData;
}
