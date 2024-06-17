package edu.miu.attendance.domain;

import edu.miu.attendance.enumType.GenderType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="Person")
@SecondaryTable(
        name="PersonAccount",
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
)
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public abstract class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;
    @Enumerated(EnumType.STRING)
    @Column(name = "GenderType")
    private GenderType genderType;
    @Column(name="birthdate")
    private LocalDate birthDate;
    @Column(name = "EmailAddress")
    private String emailAddress;
    @Column(name = "username", table = "PersonAccount")
    private String username;
    @Column(name = "password", table = "PersonAccount")
    private String password;

//    @Column(name = "role", table = "PersonAccount")
//    @ElementCollection
//    @CollectionTable(name = "UserRoles",
//            joinColumns = {@JoinColumn(name = "id", referencedColumnName = "id")}
//    )
//    private List<String> roles;

    @Embedded
    private AuditData data;

}
