package edu.miu.attendance.controller;

import edu.miu.attendance.dto.AttendanceRecordDTO;
import edu.miu.attendance.dto.StudentDTO;
import edu.miu.attendance.service.AttendanceRecordService;
import edu.miu.attendance.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/student-view")
public class AttendanceRecordController {

    @Autowired
    private AttendanceRecordService attendanceService;

    @Autowired
    private StudentService studentService;

    @GetMapping("/attendance-records")
    public Page<AttendanceRecordDTO> getAttendanceRecords(@AuthenticationPrincipal User currentUser,
                                                          Pageable pageable) {
        StudentDTO studentDTO = studentService.getStudentByUsername(currentUser.getUsername());
        return attendanceService.getAttendanceRecordsForStudent(studentDTO.getStudentId(),
                pageable);
    }
}