package edu.miu.attendance.service;

import edu.miu.attendance.dto.CourseOfferingDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CourseOfferingService {

    Page<CourseOfferingDto> findAll(Pageable pageable);
    CourseOfferingDto findById(long id) ;
    CourseOfferingDto saveCourseOffering(CourseOfferingDto courseOfferingDto,Long courseOfferingId);
    CourseOfferingDto deleteCourseOffering(long id);
    List<CourseOfferingDto> findByDate(String date) ;
}
