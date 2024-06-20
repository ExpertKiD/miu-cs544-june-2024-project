package edu.miu.attendance.repository;

import edu.miu.attendance.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findStudentByStudentId(String studentId);

    Optional<Student> findStudentByUsername(String username);

    @Query("SELECT s FROM Student s JOIN FETCH s.coursesRegistrations cr " +
            "WHERE cr.id = :courseOfferingId")
    List<Student> findStudentsByCoursesRegistrationForCourseOfferingId(@Param(
            "courseOfferingId") Long courseOfferingId);

    void deleteByStudentId(String studentId);
}
