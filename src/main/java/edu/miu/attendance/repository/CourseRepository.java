package edu.miu.attendance.repository;

import edu.miu.attendance.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {

    Optional<Course> findByName(String courseName);

    Optional<Course> findByCode(String courseCode);
}
