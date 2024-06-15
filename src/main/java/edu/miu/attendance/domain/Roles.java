package edu.miu.attendance.domain;

import edu.miu.attendance.enumType.ERole;
import jakarta.persistence.*;


@Entity
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20,unique = true)
    private ERole name;

    public Roles() {

    }

    public Roles(ERole name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ERole getName() {
        return name;
    }

    public void setName(ERole name) {
        this.name = name;
    }

}
