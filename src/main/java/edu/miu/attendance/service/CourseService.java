package edu.miu.attendance.service;

import edu.miu.attendance.domain.Course;
import edu.miu.attendance.dto.CourseDTO;
import edu.miu.attendance.repository.CourseRepository;
import edu.miu.attendance.utility.MessageUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional
@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    MessageUtility messageUtility = new MessageUtility();

    public ResponseEntity<?> addCourses(CourseDTO courseDTO) {
        try {
            Course course = new Course();
            course.setName(courseDTO.getName());
            course.setCode(courseDTO.getCode());
            course.setCredits(courseDTO.getCredits());
            course.setDepartment(courseDTO.getDepartment());
            course.setDescription(courseDTO.getDescription());
            courseRepository.save(course);
            return ResponseEntity.ok(courseDTO);
        }catch (Exception e) {
        return ResponseEntity.ok(HttpStatus.EXPECTATION_FAILED);
        }
    }

    public ResponseEntity<?> getById(Long id) {
        CourseDTO courseDTO = new CourseDTO();
        try {

            Optional<Course> courseOpt = courseRepository.findById(id);
            if (!courseOpt.isPresent()) {
                return ResponseEntity.ok(messageUtility.idNotFoundMessage());
            } else {
                List<CourseDTO> courseList = new ArrayList();
                courseDTO.setId(courseOpt.get().getId());
                courseDTO.setName(courseOpt.get().getName());
                courseDTO.setCode(courseOpt.get().getCode());
                courseDTO.setDepartment(courseOpt.get().getDepartment());
                courseDTO.setCredits(courseOpt.get().getCredits());
                courseDTO.setDescription(courseOpt.get().getDescription());
                courseList.add(courseDTO);
                return ResponseEntity.ok(courseList);
            }

        } catch (Exception e) {
            return ResponseEntity.ok(HttpStatus.EXPECTATION_FAILED);
        }
    }

    public ResponseEntity<?> getAllCourse() {
        List<Map<String, Object>> courseMapList = new ArrayList();
        try {
            List<Course> allCourseList = courseRepository.findAll();
            for (Course val : allCourseList) {
                Map<String, Object> courseMap = new HashMap();

                courseMap.put("id", val.getId());
                courseMap.put("name", val.getName());
                courseMap.put("code", val.getCode());
                courseMap.put("department", val.getDepartment());
                courseMap.put("credits",val.getCredits());
                courseMap.put("description",val.getDescription());
                courseMapList.add(courseMap);
            }

        } catch (Exception e) {
            return ResponseEntity.ok(HttpStatus.EXPECTATION_FAILED);
        }
        return ResponseEntity.ok(courseMapList);
    }

    public ResponseEntity<?> updateCourse(CourseDTO courseDTO) {
       try {
           Optional<Course> courseOpt = courseRepository.findById(courseDTO.getId());

           if(courseOpt.isPresent()) {
               Course course = courseOpt.get();
               course.setName(courseDTO.getName());
               course.setCode(courseDTO.getCode());
               course.setCredits(courseDTO.getCredits());
               course.setDepartment(courseDTO.getDepartment());
               course.setDescription(courseDTO.getDescription());
               courseRepository.save(course);

           }
       }  catch (Exception e) {
        return ResponseEntity.ok(HttpStatus.EXPECTATION_FAILED);
     }
        return ResponseEntity.ok(courseDTO);
    }

}
