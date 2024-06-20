package edu.miu.attendance.service;

import edu.miu.attendance.dto.AttendanceRecordDTO;
import edu.miu.attendance.dto.AttendanceRecordExcelDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AttendanceRecordService {
    Page<AttendanceRecordDTO> getAttendanceRecordsForStudent(Long studentId, Pageable pageable);
}
