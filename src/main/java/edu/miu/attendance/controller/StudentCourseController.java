package edu.miu.attendance.controller;

import edu.miu.attendance.domain.Student;
import edu.miu.attendance.dto.StudentCourseDTO;
import edu.miu.attendance.dto.StudentDTO;
import edu.miu.attendance.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/student-view")
public class StudentCourseController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/course-offerings")
    public List<StudentCourseDTO> registeredCourses( @AuthenticationPrincipal User currentUser){
        Optional<Student> studentOpt =
                studentService.studentByUsername(currentUser.getUsername());

       return studentService.findCourseOfferingsByStudentId(studentOpt.get().getId());
    }
}
