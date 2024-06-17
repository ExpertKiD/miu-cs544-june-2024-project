//package edu.miu.attendance.controller;
//
//import edu.miu.attendance.dto.AttendanceRecordDTO;
//import edu.miu.attendance.service.AttendanceService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import java.security.Principal;
//import java.util.List;
//
//@RestController
//@RequestMapping("/student-view")
//public class AttendanceRecordController {
//    @Autowired
//    private AttendanceService attendanceService;
//
//    @GetMapping("/attendance-records")
//    public List<AttendanceRecordDTO> getAttendanceRecords(Principal principal) {
//        // Assuming the username is the student ID
//        String studentId = principal.getName();
//        Long studentIdAsLong = Long.valueOf(studentId);
//        return attendanceService.getAttendanceRecordsForStudent(studentIdAsLong);
//    }
//}
