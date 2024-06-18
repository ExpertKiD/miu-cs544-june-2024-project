package edu.miu.attendance.controller;

import edu.miu.attendance.dto.AttendanceRecordDTO;
import edu.miu.attendance.service.AttendanceRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/student-view")
public class AttendanceRecordController {

    @Autowired
    private AttendanceRecordService attendanceService;

    @GetMapping("/attendance-records")
    public Page<AttendanceRecordDTO> getAttendanceRecords(Principal principal, Pageable pageable) {
        String studentId = principal.getName();
        Long studentIdAsLong = Long.valueOf(studentId);  // Converting it to Long if needed
        return attendanceService.getAttendanceRecordsForStudent(studentIdAsLong, pageable);
    }
}
