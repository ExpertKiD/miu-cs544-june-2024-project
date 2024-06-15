package edu.miu.attendance.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CourseDTO {
    private Long id;
    private double credits;
    private String description;
    private String code;
    private String name;
    private String department;
}
