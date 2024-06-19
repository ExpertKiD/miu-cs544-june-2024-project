package edu.miu.attendance.controller;

import edu.miu.attendance.dto.CourseOfferingDto;
import edu.miu.attendance.dto.CourseOfferingStudentAttendanceDTO;
import edu.miu.attendance.dto.StudentDTO;
import edu.miu.attendance.repository.StudentRepository;
import edu.miu.attendance.service.CourseOfferingServiceImpl;
import edu.miu.attendance.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class CourseOfferingController {
    @Autowired
    private CourseOfferingServiceImpl courseOfferingService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/student-view/course-offerings/{offeringId}")
    public ResponseEntity<?> getCourseOfferingsById(@PathVariable long offeringId) {
        CourseOfferingDto courseOfferingDto = courseOfferingService.findById(offeringId);
        return ResponseEntity.ok(courseOfferingDto);
    }

    @GetMapping("/sys-admin/course-offerings")
    public ResponseEntity<Page<?>> getAllCourseOfferings(Pageable pageable) {
        Page<CourseOfferingDto> courseOfferingDto = courseOfferingService.findAll(pageable);
        return ResponseEntity.ok(courseOfferingDto);

    }

    @PostMapping("/sys-admin/course-offerings")
    public ResponseEntity<?> createCourseOffering(@RequestBody CourseOfferingDto courseOfferingDto) {
        CourseOfferingDto result = courseOfferingService.saveCourseOffering(courseOfferingDto, null);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/sys-admin/course-offerings/{offeringId}")
    public ResponseEntity<?> updateCourseOffering(@PathVariable("offeringId") Long courseOfferingId, @RequestBody CourseOfferingDto courseOfferingDto) {
        CourseOfferingDto result = courseOfferingService.saveCourseOffering(courseOfferingDto, courseOfferingId);
        return ResponseEntity.ok(result);
    }


    @DeleteMapping("/sys-admin/course-offerings/{offeringId}")
    public ResponseEntity<?> deleteCourseOffering(@PathVariable long offeringId) {
        CourseOfferingDto courseOfferingDto = courseOfferingService.deleteCourseOffering(offeringId);
        return ResponseEntity.ok(courseOfferingDto);
    }

    @GetMapping("/student-view/course-offerings/{offeringId}/attendance")
    public ResponseEntity<?> getStudentAttendanceByStudentId(@PathVariable(
            "offeringId") Long courseOfferingId, @AuthenticationPrincipal User currentUser) {
        StudentDTO std =
                studentService.getStudentByUsername(currentUser.getUsername());


        CourseOfferingStudentAttendanceDTO attendanceDTO =
                courseOfferingService.getCourseOfferingAttendanceByStudentId(
                        std.getStudentId(),
                        courseOfferingId
                );

        return ResponseEntity.ok(attendanceDTO);
    }

}
