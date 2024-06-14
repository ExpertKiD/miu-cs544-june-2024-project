package edu.miu.attendance.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "LocationType")
@Data
public class LocationType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "type")
    private String type;
}
