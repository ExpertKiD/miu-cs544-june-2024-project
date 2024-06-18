package edu.miu.attendance.repository;

import edu.miu.attendance.domain.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
}
