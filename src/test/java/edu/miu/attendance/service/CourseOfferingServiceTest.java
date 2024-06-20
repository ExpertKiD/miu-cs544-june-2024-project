package edu.miu.attendance.service;

import edu.miu.attendance.domain.*;
import edu.miu.attendance.dto.CourseOfferingDto;
import edu.miu.attendance.exception.ResourceNotFoundException;
import edu.miu.attendance.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
class CourseOfferingServiceTest {

    @Mock
    private CourseOfferingRepository courseOfferingRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private FacultyRepository facultyRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private SessionRepository sessionRepository;

    @InjectMocks
    private CourseOfferingServiceImpl courseOfferingService;

    private Student student;
    private CourseOffering courseOffering;
    private Session activeSession;
    private AttendanceRecord attendanceRecord;

    @BeforeEach
    void setUp() {
        // Create mock student
        student = new Student();
        student.setStudentId("student1");

        // Create mock course offering
        courseOffering = new CourseOffering();
        courseOffering.setId(1L);
        courseOffering.setSessions(new ArrayList<>()); // Initialize empty session list

        // Create active session
        activeSession = new Session();
        activeSession.setId(1L);
        activeSession.setSessionDate(LocalDate.now());
        activeSession.setEndTime(LocalTime.now().plusHours(1));

        courseOffering.getSessions().add(activeSession);

        // Create mock attendance record
        attendanceRecord = new AttendanceRecord();
        attendanceRecord.setId(1L);
        attendanceRecord.setSession(activeSession);
        attendanceRecord.setCourseOffering(courseOffering);
        attendanceRecord.setStudent(student);
    }

    @Test
    void testFindAll() {
        CourseOffering courseOffering = new CourseOffering();
        CourseOfferingDto courseOfferingDto = new CourseOfferingDto();
        Pageable pageable = PageRequest.of(0, 10);
        Page<CourseOffering> courseOfferingPage = new PageImpl<>(Collections.singletonList(courseOffering));

        when(courseOfferingRepository.findAll(pageable)).thenReturn(courseOfferingPage);
        when(modelMapper.map(any(CourseOffering.class), eq(CourseOfferingDto.class))).thenReturn(courseOfferingDto);

        Page<CourseOfferingDto> result = courseOfferingService.findAll(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(courseOfferingRepository, times(1)).findAll(pageable);
    }

    @Test
    void testFindById() {
        CourseOffering courseOffering = new CourseOffering();
        CourseOfferingDto courseOfferingDto = new CourseOfferingDto();

        when(courseOfferingRepository.findById(anyLong())).thenReturn(Optional.of(courseOffering));
        when(modelMapper.map(any(CourseOffering.class), eq(CourseOfferingDto.class))).thenReturn(courseOfferingDto);

        CourseOfferingDto result = courseOfferingService.findById(1L);

        assertNotNull(result);
        verify(courseOfferingRepository, times(1)).findById(1L);
    }

    @Test
    void testFindById_NotFound() {
        when(courseOfferingRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> courseOfferingService.findById(1L));
        verify(courseOfferingRepository, times(1)).findById(1L);
    }

    @Test
    void testSaveCourseOffering() {
        CourseOfferingDto courseOfferingDto = new CourseOfferingDto();
        courseOfferingDto.setCourse_id(1L);
        courseOfferingDto.setFaculty_id(1L);
        courseOfferingDto.setSessions_id(Arrays.asList(1L, 2L));
        CourseOffering courseOffering = new CourseOffering();
        Course course = new Course();
        Faculty faculty = new Faculty();
        Session session1 = new Session();
        Session session2 = new Session();

        when(modelMapper.map(any(CourseOfferingDto.class), eq(CourseOffering.class))).thenReturn(courseOffering);
        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(course));
        when(facultyRepository.findById(anyLong())).thenReturn(Optional.of(faculty));
        when(sessionRepository.findAllById(anyList())).thenReturn(Arrays.asList(session1, session2));
        when(courseOfferingRepository.save(any(CourseOffering.class))).thenReturn(courseOffering);
        when(modelMapper.map(any(CourseOffering.class), eq(CourseOfferingDto.class))).thenReturn(courseOfferingDto);

        CourseOfferingDto result = courseOfferingService.saveCourseOffering(courseOfferingDto, null);

        assertNotNull(result);
        verify(courseOfferingRepository, times(1)).save(courseOffering);
    }

    @Test
    void testDeleteCourseOffering() {
        CourseOffering courseOffering = new CourseOffering();
        CourseOfferingDto courseOfferingDto = new CourseOfferingDto();

        when(courseOfferingRepository.findById(anyLong())).thenReturn(Optional.of(courseOffering));
        doNothing().when(courseOfferingRepository).deleteById(anyLong());
        when(modelMapper.map(any(CourseOffering.class), eq(CourseOfferingDto.class))).thenReturn(courseOfferingDto);

        CourseOfferingDto result = courseOfferingService.deleteCourseOffering(1L);

        assertNotNull(result);
        verify(courseOfferingRepository, times(1)).findById(1L);
        verify(courseOfferingRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteCourseOffering_NotFound() {
        when(courseOfferingRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> courseOfferingService.deleteCourseOffering(1L));
        verify(courseOfferingRepository, times(1)).findById(1L);
    }

    @Test
    void testGetCourseOfferingAttendanceByStudentId_StudentNotFound() {
        // Mock student repository method to return empty optional
        when(studentRepository.findStudentByStudentId(anyString())).thenReturn(Optional.empty());

        // Test for student not found
        assertThrows(ResourceNotFoundException.class,
                () -> courseOfferingService.getCourseOfferingAttendanceByStudentId("nonexistentStudent", 1L));
    }

    @Test
    void testGetCourseOfferingAttendanceByStudentId_CourseOfferingNotEnrolled() {
        // Mock student repository method
        when(studentRepository.findStudentByStudentId(anyString())).thenReturn(Optional.of(student));

        // Mock student's coursesRegistrations to return empty list (not enrolled in course offering)
        student.setCoursesRegistrations(new ArrayList<>());

        // Test for course offering not enrolled
        assertThrows(ResourceNotFoundException.class,
                () -> courseOfferingService.getCourseOfferingAttendanceByStudentId("student1", 1L));
    }
}
