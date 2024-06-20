package edu.miu.attendance.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StudentCourseDTO {
    private String code;
    private String name;
    private String description;
    private double credits;
    private String grade;
}
