package edu.miu.attendance.dto;

import edu.miu.attendance.domain.Course;
import edu.miu.attendance.domain.Faculty;
import edu.miu.attendance.domain.Session;
import edu.miu.attendance.enumType.CourseOfferingType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
public class CourseOfferingDto {

    private Long id;
    private double credits;
    private CourseOfferingType courseOfferingType;
    private int capacity;
    private String room;
    private CourseDTO course;
    private FacultyDto faculty;
    private List<SessionDto> sessions;

    private Long course_id;
    private Long faculty_id;
    private List<Long> sessions_id;
}
