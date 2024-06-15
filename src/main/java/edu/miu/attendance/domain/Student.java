package edu.miu.attendance.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Student")
@Data
public class Student extends Person {
    @Column(name = "Entry")
    private String entry;

    @Column(name = "AlternateID")
    private String alternateID;

    @Column(name = "ApplicantID")
    private String applicantId;

    @Column(name = "StudentID")
    private String studentId;

    @ManyToOne
    @JoinColumn(name = "FacultyAdivserID")
    private Faculty facultyAdviser;

    @ManyToMany
    @JoinTable(name = "CourseRegistration",
            inverseJoinColumns = @JoinColumn(name = "CourseOfferingId", referencedColumnName = "id"),
            joinColumns = @JoinColumn(name = "StudentId", referencedColumnName = "id")
    )
    private List<Course> coursesRegistrations;

    @OneToMany(mappedBy = "student")
    private List<AttendanceRecord> attendanceRecords;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Roles> roles = new HashSet<>();


}
