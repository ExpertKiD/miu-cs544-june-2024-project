package edu.miu.attendance.dto;

import edu.miu.attendance.domain.enums.GenderType;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class StudentDTO {
    private String firstName;
    private String lastName;
    private GenderType genderType;
    private LocalDate birthDate;
    private String emailAddress;
    private String entry;
    private String alternateID;
    private String applicantId;
    private String studentId;
    private List<CourseDTO> courses;
}
