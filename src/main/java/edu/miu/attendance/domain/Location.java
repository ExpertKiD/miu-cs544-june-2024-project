package edu.miu.attendance.domain;

import edu.miu.attendance.domain.enums.LocationType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Location")
@Data
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int capacity;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private LocationType locationType;

    @Embedded
    private AuditData auditData = new AuditData();
}
