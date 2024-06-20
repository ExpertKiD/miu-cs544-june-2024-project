package edu.miu.attendance.service;


import edu.miu.attendance.domain.Student;

import edu.miu.attendance.dto.StudentCourseDTO;
import edu.miu.attendance.dto.StudentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;


public interface StudentService {
    StudentDTO addStudent(StudentDTO studentDTO);

    Page<StudentDTO> getAllStudents(Pageable pageable);

    StudentDTO getStudentByStudentId(String studentId);

    StudentDTO getStudentByUsername(String username);

    StudentDTO updateStudent(String studentId, StudentDTO studentDTO);

    StudentDTO getStudentWithCourses(String studentId);

    void deleteStudentByStudentId(String studentId);

    List<StudentCourseDTO> findCourseOfferingsByStudentId(Long studentId);

    Optional<Student> studentByUsername(String username);
    
    List<StudentDTO> findStudentsByCoursesRegistrationForCourseOfferingId(Long courseOfferingId);

}
