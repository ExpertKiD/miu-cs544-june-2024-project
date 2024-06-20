package edu.miu.attendance.service;

import edu.miu.attendance.domain.Student;
import edu.miu.attendance.domain.enums.GenderType;
import edu.miu.attendance.dto.StudentDTO;
import edu.miu.attendance.exception.ResourceNotFoundException;
import edu.miu.attendance.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class StudentServiceTests {

    @Autowired
    private StudentService studentService;
    @MockBean
    private StudentRepository studentRepository;
    private StudentDTO studentDTO;

    @BeforeEach
    public void setUp() {
        studentDTO = getStudentDTO();
        Student student = getStudentEntity();

        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentRepository.findStudentByStudentId(student.getStudentId())).thenReturn(Optional.of(student));
        Page<Student> studentPage = new PageImpl<>(Collections.singletonList(student));
        when(studentRepository.findAll(any(Pageable.class))).thenReturn(studentPage);
        doNothing().when(studentRepository).deleteByStudentId(anyString());
    }

    @Test
    void testSaveStudent() {
        studentDTO.setStudentId("123456");
        StudentDTO savedStudent = studentService.addStudent(studentDTO);

        assertThat(savedStudent, is(notNullValue()));
        assertThat(savedStudent.getFirstName(), is("John"));
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void testGetAllStudents() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<StudentDTO> result = studentService.getAllStudents(pageable);

        assertThat(result, is(notNullValue()));
        assertThat(result.getContent(), hasSize(1));
        assertThat(result.getContent().get(0).getFirstName(), is("John"));
        verify(studentRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void testGetStudentById() {
        StudentDTO foundStudent = studentService.getStudentByStudentId("12345");

        assertThat(foundStudent, is(notNullValue()));
        assertThat(foundStudent.getStudentId(), is("12345"));
        verify(studentRepository, times(1)).findStudentByStudentId(anyString());
    }

    @Test
    void testGetStudentById_ResourceNotFoundException() {
        when(studentRepository.findStudentByStudentId(anyString())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            studentService.getStudentByStudentId("12345");
        });

        assertThat(exception.getMessage(), is("Student with studentId #12345 doesn't exist"));
        verify(studentRepository, times(1)).findStudentByStudentId(anyString());
    }

    @Test
    void testUpdateStudent() {
        StudentDTO updatedStudent = studentService.updateStudent(studentDTO.getStudentId(), studentDTO);

        assertThat(updatedStudent, is(notNullValue()));
        assertThat(updatedStudent.getFirstName(), is("John"));
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void testUpdateStudent_ResourceNotFoundException() {
        when(studentRepository.findStudentByStudentId(anyString())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            studentService.updateStudent("12345", studentDTO);
        });

        assertThat(exception.getMessage(), is("Student with studentId #12345 doesn't exist"));
        verify(studentRepository, times(1)).findStudentByStudentId(anyString());
    }

    @Test
    void testDeleteStudent() {
        studentService.deleteStudentByStudentId("12345");

        verify(studentRepository, times(1)).deleteByStudentId(anyString());
    }

    @Test
    void testDeleteStudent_ResourceNotFoundException() {
        doThrow(new ResourceNotFoundException("Student with studentId #12345 doesn't exist")).when(studentRepository).deleteByStudentId(anyString());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            studentService.deleteStudentByStudentId("12345");
        });

        assertThat(exception.getMessage(), is("Student with studentId #12345 doesn't exist"));
        verify(studentRepository, times(1)).deleteByStudentId(anyString());
    }

    private StudentDTO getStudentDTO() {
        StudentDTO student = new StudentDTO();
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

    @Test
    public void testFindStudentsByCoursesRegistrationForCourseOfferingId() {
        // Mock data
        Student student1 = new Student();
        student1.setStudentId("student1");

        Student student2 = new Student();
        student2.setStudentId("student2");

        List<Student> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);

        // Mocking repository method
        when(studentRepository.findStudentsByCoursesRegistrationForCourseOfferingId(anyLong()))
                .thenReturn(students);

        // Call the service method
        List<StudentDTO> result =
                studentService.findStudentsByCoursesRegistrationForCourseOfferingId(1L);

        // Assert the result
        assertEquals(2, result.size());
        assertEquals("student1", result.get(0).getStudentId());
        assertEquals("student2", result.get(1).getStudentId());
    }

    @TestConfiguration
    static class StudentServiceTestContextConfiguration {
        @Bean
        public StudentService studentService() {
            return new StudentServiceImpl();
        }
    }
}
