package edu.miu.attendance.controller;

import edu.miu.attendance.dto.StudentCourseDTO;
import edu.miu.attendance.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/student-view")
public class StudentCourseController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/course-offerings")
    public List<StudentCourseDTO> registeredCourses(@RequestHeader("studentId") Long studentId){
       return studentService.findCourseOfferingsByStudentId(studentId);
    }
}
