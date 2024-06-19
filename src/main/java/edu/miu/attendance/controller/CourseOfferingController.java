package edu.miu.attendance.controller;

import edu.miu.attendance.dto.CourseOfferingDto;
import edu.miu.attendance.service.CourseOfferingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CourseOfferingController {

    @Autowired
    private CourseOfferingServiceImpl courseOfferingService;

    @GetMapping("/student-view/course-offerings/{offeringId}")
    public ResponseEntity<?> getCourseOfferingsById(@PathVariable long offeringId){
        CourseOfferingDto courseOfferingDto=courseOfferingService.findById(offeringId);
        return ResponseEntity.ok(courseOfferingDto);
    }

    @GetMapping("/sys-admin/course-offerings")
    public ResponseEntity<Page<?>> getAllCourseOfferings(Pageable pageable){
        Page<CourseOfferingDto> courseOfferingDto = courseOfferingService.findAll(pageable);
        return ResponseEntity.ok(courseOfferingDto);

    }

    @PostMapping("/sys-admin/course-offerings")
    public ResponseEntity<?> createCourseOffering(@RequestBody CourseOfferingDto courseOfferingDto){
        CourseOfferingDto result=courseOfferingService.saveCourseOffering(courseOfferingDto, null );
        return ResponseEntity.ok(result);
    }

    @PutMapping("/sys-admin/course-offerings/{offeringId}")
    public ResponseEntity<?> updateCourseOffering(@PathVariable("offeringId") Long courseOfferingId, @RequestBody CourseOfferingDto courseOfferingDto){
        CourseOfferingDto result=courseOfferingService.saveCourseOffering(courseOfferingDto,courseOfferingId);
        return ResponseEntity.ok(result);
    }


    @DeleteMapping("/sys-admin/course-offerings/{offeringId}")
    public ResponseEntity<?> deleteCourseOffering(@PathVariable long offeringId){
        CourseOfferingDto courseOfferingDto=courseOfferingService.deleteCourseOffering(offeringId);
        return ResponseEntity.ok(courseOfferingDto);
    }

    @GetMapping("/admin-view/course-offerings")
    public ResponseEntity<?> getCourseOfferingsById(@RequestParam("date") String date){
        List<CourseOfferingDto> courseOfferingDto=courseOfferingService.findByDate(date);
        return ResponseEntity.ok(courseOfferingDto);
    }

}
