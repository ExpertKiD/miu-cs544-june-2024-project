package edu.miu.attendance.repository;

import edu.miu.attendance.domain.CourseOffering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface CourseOfferingRepository extends JpaRepository<CourseOffering, Long> {

}
