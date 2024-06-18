package edu.miu.attendance.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AttendanceRecordDTO {
    private Long id;
    private LocalDateTime scanDateTime;
    private String locationName;
    private String locationType;
    private String courseOfferingName;
}
