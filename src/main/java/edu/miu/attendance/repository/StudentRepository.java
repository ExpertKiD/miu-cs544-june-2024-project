package edu.miu.attendance.repository;

import edu.miu.attendance.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findStudentByStudentId(String studentId);

    Optional<Student> findStudentByUsername(String username);

    void deleteByStudentId(String studentId);
}
