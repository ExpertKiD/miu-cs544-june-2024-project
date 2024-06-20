package edu.miu.attendance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@Getter
@Setter
public class AttendanceRecordExcelDTO {

    private String studentid;
    private String firstName;
    private String lastName;
    private String facultyFirstName;
    private String facultyLastName;
    private String CourseCode;
    private String CourseName;
    private String department;
    private double credits;
    private LocalDateTime scanDateTime;
    private String name;
    private String type;


}
