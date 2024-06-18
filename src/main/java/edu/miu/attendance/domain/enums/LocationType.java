package edu.miu.attendance.domain.enums;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "LocationType")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "type")
    private String type;
}
