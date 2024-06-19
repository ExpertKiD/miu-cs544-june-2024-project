package edu.miu.attendance.controller;

import edu.miu.attendance.dto.StudentDTO;
import edu.miu.attendance.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin-view")
public class AdminController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/students/{studentId}")
    public StudentDTO getStudentDetails(@PathVariable String studentId) {
        return studentService.getStudentWithCourses(studentId);
    }
}
