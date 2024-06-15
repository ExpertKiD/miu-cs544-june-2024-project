package edu.miu.attendance.dto;

import edu.miu.attendance.domain.AuditData;
import edu.miu.attendance.domain.LocationType;
import lombok.Data;

@Data
public class LocationDTO {
    private Long id;
    private String name;
    private int capacity;
    private LocationType locationType;
    private AuditData auditData;
}
