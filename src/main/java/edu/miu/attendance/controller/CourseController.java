package edu.miu.attendance.controller;

import edu.miu.attendance.dto.CourseDTO;
import edu.miu.attendance.service.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class CourseController {

    private CourseService courseService;

    @PostMapping("/courses")
    public ResponseEntity<?> save(@RequestBody CourseDTO courseDTO) {
        return courseService.addCourses(courseDTO);
    }

    @GetMapping("/courses/{id}")
    public ResponseEntity<?> singleCourse(@PathVariable("id") Long id) {
        return courseService.getById(id);
    }

    @GetMapping("/courses")
    public ResponseEntity<?> allCourse() {
        return courseService.getAllCourse();
    }

    @PutMapping("/courses")
    public ResponseEntity<?> updateCourse(@RequestBody CourseDTO courseDTO) {
        return courseService.updateCourse(courseDTO);
    }
}
