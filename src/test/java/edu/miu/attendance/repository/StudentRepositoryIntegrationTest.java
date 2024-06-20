package edu.miu.attendance.repository;

import edu.miu.attendance.domain.Course;
import edu.miu.attendance.domain.CourseOffering;
import edu.miu.attendance.domain.Student;
import edu.miu.attendance.domain.enums.CourseOfferingType;
import edu.miu.attendance.domain.enums.GenderType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class StudentRepositoryIntegrationTest {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private CourseOfferingRepository courseOfferingRepository;

    @Test
    void whenFindByStudentId_thenReturnStudent() {
        // given
        Student student = getStudentEntity();
        studentRepository.save(student);

        // when
        Optional<Student> found = studentRepository.findStudentByStudentId(student.getStudentId());

        // then
        assertThat(found).isPresent();
        assertThat(found.get().getFirstName()).isEqualTo(student.getFirstName());
    }

    @Test
    void whenDeleteByStudentId_thenStudentShouldBeDeleted() {
        // given
        Student student = getStudentEntity();
        studentRepository.save(student);

        // when
        studentRepository.deleteByStudentId(student.getStudentId());
        Optional<Student> found = studentRepository.findStudentByStudentId(student.getStudentId());

        // then
        assertThat(found).isNotPresent();
    }

    @Test
    void testFindStudentsByCoursesRegistrationForCourseOfferingId() {
        // Creating a Course
        Course course = new Course();
        course.setName("Software Engineering");
        course.setCode("SE101");
        course.setCredits(4.0);
        course.setDescription("Introduction to Software Engineering");
        course.setDepartment("Computer Science");
        courseRepository.save(course);

// Creating a CourseOffering
        CourseOffering courseOffering = new CourseOffering();
        courseOffering.setCourse(course);
        courseOffering.setCourseOfferingType(CourseOfferingType.ON_CAMPUS); // Assuming CourseOfferingType is an enum
        courseOffering.setCapacity(30); // Maximum capacity of students
        courseOffering.setRoom("Main Hall");
        courseOffering.setSessions(new ArrayList<>());
        courseOfferingRepository.save(courseOffering);

// Creating a Student
        Student student = new Student();
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setGenderType(GenderType.MALE); // Assuming GenderType is an enum
        student.setBirthDate(LocalDate.of(1995, 5, 15));
        student.setEmailAddress("john.doe@example.com");
        student.setUsername("johndoe");
        student.setPassword("password123");
        student.setStudentId("ST1001");


// Creating Course Registrations for the Student
        List<CourseOffering> courseRegistrations = new ArrayList<>();
        courseRegistrations.add(courseOffering);

        student.setCoursesRegistrations(courseRegistrations);
        studentRepository.save(student);


        // Call the method
        List<Student> result =
                studentRepository.findStudentsByCoursesRegistrationForCourseOfferingId(courseOffering.getId());

        // Assert the results
        assertEquals(1, result.size());
        assertEquals("ST1001", result.get(0).getStudentId());
    }

    private Student getStudentEntity() {
        Student student = new Student();
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setGenderType(GenderType.MALE);
        student.setBirthDate(LocalDate.of(1990, 1, 1));
        student.setEmailAddress("john.doe@example.com");
        student.setEntry("2021FALL");
        student.setAlternateID("ALT123");
        student.setApplicantId("APP123");
        student.setStudentId("12345");

        return student;
    }


}
