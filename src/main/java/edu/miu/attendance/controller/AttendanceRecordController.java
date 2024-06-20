package edu.miu.attendance.controller;

import edu.miu.attendance.dto.AttendanceRecordDTO;
import edu.miu.attendance.dto.AttendanceRecordExcelDTO;
import edu.miu.attendance.service.AttendanceRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/student-view")
public class AttendanceRecordController {

    @Autowired
    private AttendanceRecordService attendanceService;

    @GetMapping("/attendance-records")
    public Page<AttendanceRecordDTO> getAttendanceRecords(@RequestParam Long studentId, Pageable pageable) {
        return attendanceService.getAttendanceRecordsForStudent(studentId, pageable);
    }
}
