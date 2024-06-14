package edu.miu.attendance.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "AttendanceRecord")
public class AttendanceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ScanDateTime")
    private LocalDateTime scanDateTime;

    @ManyToOne
    @JoinColumn(name = "StudentId")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "LocationId")
    private Location location;


}
