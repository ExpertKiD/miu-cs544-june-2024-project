package edu.miu.attendance.domain;

import edu.miu.attendance.domain.enums.RoleType;
import jakarta.persistence.*;
import lombok.Data;


@Table(name = "roles")
@Entity
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, unique = true)
    private RoleType roleType;
}
