package edu.miu.attendance.domain;

import edu.miu.attendance.domain.enums.GenderType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Person")
@SecondaryTable(
        name = "PersonAccount",
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
)
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public abstract class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;
    @Enumerated(EnumType.STRING)
    @Column(name = "GenderType")
    private GenderType genderType;
    @Column(name = "birthdate")
    private LocalDate birthDate;
    @Column(name = "EmailAddress")
    private String emailAddress;
    @Column(name = "username", table = "PersonAccount")
    private String username;
    @Column(name = "password", table = "PersonAccount")
    private String password;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @Embedded
    private AuditData auditData;

    @PrePersist
    protected void onCreate() {
        if (auditData == null) {
            auditData = new AuditData();
        }

        if (roles == null) {
            roles = new HashSet<>();
        }
    }

}
