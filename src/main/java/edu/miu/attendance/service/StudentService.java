package edu.miu.attendance.service;

import edu.miu.attendance.dto.StudentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudentService {
    StudentDTO addStudent(StudentDTO studentDTO);

    Page<StudentDTO> getAllStudents(Pageable pageable);

    StudentDTO getStudentByStudentId(String studentId);

    StudentDTO getStudentByUsername(String username);

    StudentDTO updateStudent(String studentId, StudentDTO studentDTO);

    StudentDTO getStudentWithCourses(String studentId);

    void deleteStudentByStudentId(String studentId);

}
