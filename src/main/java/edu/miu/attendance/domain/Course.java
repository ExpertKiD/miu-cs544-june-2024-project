package edu.miu.attendance.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Course")
@Data
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "credits")
    private double credits;

    @Column(name = "CourseDescription")
    private String description;

    @Column(name = "CourseCode")
    private String code;

    @Column(name = "CourseName")
    private String name;

    @Column(name = "department")
    private String department;

    @Embedded
    private AuditData auditData;

    @OneToMany
    @JoinTable(name = "CoursePrequisite",
    joinColumns = {@JoinColumn(name = "CourseId", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "PrerequisiteId", referencedColumnName = "id")}
    )
    private List<Course> prerequisites = new ArrayList<>();
}
