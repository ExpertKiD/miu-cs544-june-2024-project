package edu.miu.attendance.repository;

import edu.miu.attendance.domain.AttendanceRecord;
import edu.miu.attendance.domain.Course;
import edu.miu.attendance.domain.CourseOffering;
import edu.miu.attendance.domain.Location;
import edu.miu.attendance.domain.Student;
import edu.miu.attendance.domain.enums.LocationType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class AttendanceRecordRepositoryIntegrationTest {

    @Autowired
    private AttendanceRecordRepository attendanceRecordRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private CourseOfferingRepository courseOfferingRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private LocationTypeRepository locationTypeRepository;

    @Test
    void whenFindAttendanceRecordsByStudentId_thenReturnAttendanceRecords() {
        // given
        Student student = getStudentEntity();
        studentRepository.save(student);

        LocationType locationType = getLocationTypeEntity();
        locationTypeRepository.save(locationType);

        Location location = getLocationEntity(locationType);
        locationRepository.save(location);

        Course course = getCourseEntity();
        courseRepository.save(course);

        CourseOffering courseOffering = getCourseOfferingEntity(course);
        courseOfferingRepository.save(courseOffering);

        AttendanceRecord attendanceRecord = getAttendanceRecordEntity(student, location, courseOffering);
        attendanceRecordRepository.save(attendanceRecord);

        // when
        Pageable pageable = PageRequest.of(0, 10);
        Page<AttendanceRecord> found = attendanceRecordRepository.findAttendanceRecordsByStudentId(student.getStudentId(), pageable);

        // then
        assertThat(found).isNotEmpty();
        List<AttendanceRecord> attendanceRecords = found.getContent();
        assertThat(attendanceRecords).hasSize(1);
        assertThat(attendanceRecords.get(0).getLocation().getName()).isEqualTo(location.getName());
        assertThat(attendanceRecords.get(0).getLocation().getLocationType()).isEqualTo(location.getLocationType());
        assertThat(attendanceRecords.get(0).getCourseOffering().getCourse().getName()).isEqualTo(course.getName());
    }

    @Test
    void whenDeleteById_thenAttendanceRecordShouldBeDeleted() {
        // given
        Student student = getStudentEntity();
        studentRepository.save(student);

        LocationType locationType = getLocationTypeEntity();
        locationTypeRepository.save(locationType);

        Location location = getLocationEntity(locationType);
        locationRepository.save(location);

        Course course = getCourseEntity();
        courseRepository.save(course);

        CourseOffering courseOffering = getCourseOfferingEntity(course);
        courseOfferingRepository.save(courseOffering);

        AttendanceRecord attendanceRecord = getAttendanceRecordEntity(student, location, courseOffering);
        attendanceRecordRepository.save(attendanceRecord);

        // when
        attendanceRecordRepository.deleteById(attendanceRecord.getId());
        Pageable pageable = PageRequest.of(0, 10);
        Page<AttendanceRecord> found = attendanceRecordRepository.findAttendanceRecordsByStudentId(student.getStudentId(), pageable);

        // then
        assertThat(found).isEmpty();
    }

    private Student getStudentEntity() {
        Student student = new Student();
        student.setStudentId("1");
        student.setFirstName("John");
        student.setLastName("Doe");
        // Set other student properties if needed
        return student;
    }

    private LocationType getLocationTypeEntity() {
        LocationType locationType = new LocationType();
        locationType.setType("Lecture");
        return locationType;
    }

    private Location getLocationEntity(LocationType locationType) {
        Location location = new Location();
        location.setName("Main Hall");
        location.setLocationType(locationType);
        return location;
    }

    private Course getCourseEntity() {
        Course course = new Course();
        course.setName("Software Engineering");
        return course;
    }

    private CourseOffering getCourseOfferingEntity(Course course) {
        CourseOffering courseOffering = new CourseOffering();
        courseOffering.setCourse(course);
        return courseOffering;
    }

    private AttendanceRecord getAttendanceRecordEntity(Student student, Location location, CourseOffering courseOffering) {
        AttendanceRecord attendanceRecord = new AttendanceRecord();
        attendanceRecord.setLocation(location);
        attendanceRecord.setCourseOffering(courseOffering);
        attendanceRecord.setStudent(student);
        // Set other attendance record properties if needed
        return attendanceRecord;
    }
}
