package edu.miu.attendance.dto;

import edu.miu.attendance.domain.enums.GenderType;
import lombok.Data;

@Data
public class PersonDto {

    private Long id;
    private String firstName;
    private String lastName;
    private GenderType genderType;
    private String emailAddress;
}
