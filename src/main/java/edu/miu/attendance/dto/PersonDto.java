package edu.miu.attendance.dto;

import edu.miu.attendance.domain.AuditData;
import edu.miu.attendance.enumType.GenderType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class PersonDto {

    private Long id;
    private String firstName;
    private String lastName;
    private GenderType genderType;
    private String emailAddress;
}
