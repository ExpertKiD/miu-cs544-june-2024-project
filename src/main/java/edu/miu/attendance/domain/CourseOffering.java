package edu.miu.attendance.domain;

import edu.miu.attendance.domain.enums.CourseOfferingType;
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

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Session> sessions;

    @Embedded
    private AuditData auditData;

    @PrePersist
    void onCreate() {
        if (auditData == null) {
            auditData = new AuditData();
        }
    }
}
