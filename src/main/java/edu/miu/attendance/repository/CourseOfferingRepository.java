package edu.miu.attendance.repository;

import edu.miu.attendance.domain.Course;
import edu.miu.attendance.domain.CourseOffering;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;
import java.util.List;


@Repository
public interface CourseOfferingRepository extends JpaRepository<CourseOffering, Long> {


    @Query("SELECT distinct cf FROM CourseOffering cf JOIN cf.sessions s WHERE s.sessionDate <= :date and s.sessionDate>=:date")
    List<CourseOffering> findAllCourseOfferingByDate(@Param("date") LocalDate date);


    List<CourseOffering>findByCourse(Course course);


}
