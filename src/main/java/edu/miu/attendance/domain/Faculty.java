package edu.miu.attendance.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "Faculty")
@Data
public class Faculty extends Person {
    @Column(name = "Salutation")
    private String position;

    @ElementCollection
    @CollectionTable(name = "FacultyHobby",
            joinColumns = {@JoinColumn(name = "id", referencedColumnName = "id")}
    )
    @Column(name = "hobbies")
    private List<String> hobbies;
}
