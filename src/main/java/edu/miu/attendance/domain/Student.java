package edu.miu.attendance.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Student")
@Data
public class Student extends Person {
    @Column(name = "Entry")
    private String entry;

    @Column(name = "AlternateID", unique = true)
    private String alternateID;

    @Column(name = "ApplicantID", unique = true)
    private String applicantId;

    @Column(name = "StudentID", unique = true)
    private String studentId;

    @ManyToOne
    @JoinColumn(name = "FacultyAdivserID")
    private Faculty facultyAdviser;

    @ManyToMany
    @JoinTable(name = "CourseRegistration",
            inverseJoinColumns = @JoinColumn(name = "CourseOfferingId", referencedColumnName = "id"),
            joinColumns = @JoinColumn(name = "StudentId", referencedColumnName = "id")
    )
    @OrderColumn(name = "sequence")
    private List<CourseOffering> coursesRegistrations = new ArrayList<>();

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<AttendanceRecord> attendanceRecords = new ArrayList<>();
}
