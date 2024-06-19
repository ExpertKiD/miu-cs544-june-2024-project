package edu.miu.attendance.service;

import edu.miu.attendance.domain.*;
import edu.miu.attendance.dto.AttendanceRecordDTO;
import edu.miu.attendance.dto.CourseOfferingDto;
import edu.miu.attendance.dto.CourseOfferingStudentAttendanceDTO;
import edu.miu.attendance.dto.SessionDto;
import edu.miu.attendance.exception.ResourceNotFoundException;
import edu.miu.attendance.repository.*;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.Objects;

@Service
public class CourseOfferingServiceImpl implements CourseOfferingService {

    @Autowired
    private CourseOfferingRepository courseOfferingRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private StudentRepository studentRepository;

    public Page<CourseOfferingDto> findAll(Pageable pageable) {
        return courseOfferingRepository.findAll(pageable).map(courseOffering -> modelMapper.map(courseOffering, CourseOfferingDto.class));
    }

    public CourseOfferingDto findById(long id) {
        CourseOffering courseOffering = courseOfferingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("CourseOffering doesn't exit!"));
        ;
        return modelMapper.map(courseOffering, CourseOfferingDto.class);
    }

    @Transactional
    public CourseOfferingDto saveCourseOffering(CourseOfferingDto courseOfferingDto, Long courseOfferingId) {
        CourseOffering courseOffering;
        if (courseOfferingId != null) {
            courseOffering = courseOfferingRepository.findById(courseOfferingId).orElseThrow(() -> new ResourceNotFoundException("CourseOffering doesn't exit!"));
            courseOffering = modelMapper.map(courseOfferingDto, CourseOffering.class);
            courseOffering.setId(courseOfferingId);
        } else {
            courseOffering = modelMapper.map(courseOfferingDto, CourseOffering.class);
        }


        Course course = courseRepository.findById(courseOfferingDto.getCourse_id()).orElseThrow(() -> new ResourceNotFoundException("Course doesn't exit!"));
        Faculty faculty = facultyRepository.findById(courseOfferingDto.getFaculty_id()).orElseThrow(() -> new ResourceNotFoundException("Faculty doesn't exit!"));
        List<Session> sessions = sessionRepository.findAllById(courseOfferingDto.getSessions_id());
        if (sessions.isEmpty() || sessions.size() == 0 || courseOfferingDto.getSessions_id().size() != sessions.size()) {
            throw new ResourceNotFoundException("Session doesn't exit!");
        }
        courseOffering.setCourse(course);
        courseOffering.setFaculty(faculty);
        courseOffering.setSessions(sessions);
        courseOfferingRepository.save(courseOffering);

        return modelMapper.map(courseOffering, CourseOfferingDto.class);
    }

    @Override
    @Transactional
    public CourseOfferingDto deleteCourseOffering(long id) {
        CourseOffering deletedData = courseOfferingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Data doesn't exit!"));
        courseOfferingRepository.deleteById(id);

        return modelMapper.map(deletedData, CourseOfferingDto.class);
    }

    @Override
    public CourseOfferingStudentAttendanceDTO getCourseOfferingAttendanceByStudentId(String studentId, Long courseOfferingId) {
        Student student =
                studentRepository.findStudentByStudentId(studentId).orElseThrow(() -> new ResourceNotFoundException("Student with id #" + studentId + " doesn't exist"));

        CourseOffering courseOffering =
                student.getCoursesRegistrations()
                        .stream()
                        .filter(co -> Objects.equals(co.getId(), courseOfferingId))
                        .findFirst()
                        .orElseThrow(() -> new ResourceNotFoundException("Student is not " + "enrolled in course offered by ID #" + courseOfferingId));

        final LocalDateTime now = LocalDateTime.now();

        List<Session> activeSessions =
                courseOffering.getSessions().stream()
                        .filter(session -> session.getSessionDate().atTime(session.getEndTime()).isBefore(now))
                        .toList();

        Map<Long, Session> activeSessionsMap =
                activeSessions.stream().collect(Collectors.toMap(Session::getId,
                        session -> session));

        var attendanceRecords = student.getAttendanceRecords().stream()
                .filter(attendanceRecord -> {
                    return attendanceRecord.getCourseOffering().getId().equals(courseOffering.getId()) &&
                            activeSessionsMap.get(attendanceRecord.getSession().getId()) != null
                            ;
                }).toList();

        if (activeSessions.isEmpty()) {
            throw new ResourceNotFoundException("No active sessions found");
        }

        CourseOfferingStudentAttendanceDTO attendanceDTO =
                new CourseOfferingStudentAttendanceDTO();

        attendanceDTO.setAttendance(attendanceRecords
                .stream()
                .map((element) -> modelMapper.map(element, AttendanceRecordDTO.class))
                .toList());

        attendanceDTO.setSessions(activeSessions
                .stream()
                .map((element) -> modelMapper.map(element, SessionDto.class))
                .toList());


        return attendanceDTO;
    }

    @Override
    public List<CourseOfferingDto> findByDate(String date) {
        List<CourseOffering> resultData = courseOfferingRepository.findAllCourseOfferingByDate(LocalDate.parse(date));
        return resultData.stream().map(courseOffering -> modelMapper.map(courseOffering, CourseOfferingDto.class)).collect(Collectors.toList());
    }
}
