package edu.miu.attendance.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Roles> roles = new HashSet<>();
}
