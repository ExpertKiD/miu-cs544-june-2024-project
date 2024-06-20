package edu.miu.attendance.dto;

import lombok.Data;

import java.util.List;

@Data
public class CourseOfferingStudentAttendanceDTO {

    private List<SessionDto> sessions;

    private List<AttendanceRecordExcelDTO> attendance;
}
