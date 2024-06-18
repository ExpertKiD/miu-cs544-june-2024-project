package edu.miu.attendance.dto;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.JoinColumn;
import lombok.Data;

import java.util.List;

@Data
public class FacultyDto extends PersonDto {

    private String position;
    private List<String> hobbies;
}
