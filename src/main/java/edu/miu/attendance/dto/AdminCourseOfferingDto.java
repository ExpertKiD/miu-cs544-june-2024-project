package edu.miu.attendance.dto;

import edu.miu.attendance.domain.enums.CourseOfferingType;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class AdminCourseOfferingDto {

    private Long id;
    private double credits;
    private CourseOfferingType courseOfferingType;
    private int capacity;
    private String room;
    private CourseDTO course;
    private FacultyDto faculty;
    private List<SessionDto> sessions;

    private LocalDate startDate;
    private LocalDate endDate;

    private List<StudentDTO> students;


}
