package edu.miu.attendance.controller;

import edu.miu.attendance.dto.StudentDTO;
import edu.miu.attendance.service.StudentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/sys-admin/students")
    public ResponseEntity<StudentDTO> sysAdminAddStudent(@RequestBody StudentDTO student) {
        StudentDTO studentDTO = studentService.addStudent(student);

        return new ResponseEntity<>(studentDTO, HttpStatus.CREATED);
    }


    @PostMapping("/sys-admin/students/{student-id}")
    public ResponseEntity<StudentDTO> sysAdminUpdateStudent(
            @PathVariable("student-id") String studentId,
            @RequestBody StudentDTO student) {
        StudentDTO studentDTO = studentService.updateStudent(studentId, student);

        return ResponseEntity.ok(studentDTO);
    }


    @DeleteMapping("/sys-admin/students/{student-id}")
    public ResponseEntity<Object> sysAdminDeleteStudent(@PathVariable("student-id") String studentId) {
        studentService.deleteStudentByStudentId(studentId);

        return ResponseEntity.noContent().build();
    }


    @GetMapping("/sys-admin/students/{student-id}")
    public ResponseEntity<StudentDTO> sysAdminGetStudent(@PathVariable("student-id") String studentId) {
        StudentDTO studentDTO = studentService.getStudentByStudentId(studentId);

        return ResponseEntity.ok(studentDTO);
    }


    @GetMapping("/sys-admin/students")
    public ResponseEntity<Page<StudentDTO>> sysAdminGetAllStudents(Pageable pageable) {
        Page<StudentDTO> studentDTOPage = studentService.getAllStudents(pageable);

        return ResponseEntity.ok(studentDTOPage);
    }
}
