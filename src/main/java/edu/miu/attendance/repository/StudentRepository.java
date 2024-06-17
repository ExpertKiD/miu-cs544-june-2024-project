package edu.miu.attendance.repository;

import edu.miu.attendance.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
