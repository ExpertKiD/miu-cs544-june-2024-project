package edu.miu.attendance.service;

import edu.miu.attendance.domain.Course;
import edu.miu.attendance.domain.CourseOffering;
import edu.miu.attendance.domain.Faculty;
import edu.miu.attendance.domain.Session;
import edu.miu.attendance.dto.CourseOfferingDto;
import edu.miu.attendance.exception.ResourceNotFoundException;
import edu.miu.attendance.repository.CourseOfferingRepository;
import edu.miu.attendance.repository.CourseRepository;
import edu.miu.attendance.repository.FacultyRepository;
import edu.miu.attendance.repository.SessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

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
    private SessionRepository sessionRepository;

    @InjectMocks
    private CourseOfferingServiceImpl courseOfferingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
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

        CourseOfferingDto result = courseOfferingService.saveCourseOffering("psalek", courseOfferingDto, null);

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

        CourseOfferingDto result = courseOfferingService.deleteCourseOffering("psalek" ,1L);

        assertNotNull(result);
        verify(courseOfferingRepository, times(1)).findById(1L);
        verify(courseOfferingRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteCourseOffering_NotFound() {
        when(courseOfferingRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> courseOfferingService.deleteCourseOffering("psalek",1L ));
        verify(courseOfferingRepository, times(1)).findById(1L);
    }
}
