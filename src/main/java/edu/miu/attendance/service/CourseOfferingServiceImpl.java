package edu.miu.attendance.service;

import edu.miu.attendance.domain.Course;
import edu.miu.attendance.domain.CourseOffering;
import edu.miu.attendance.domain.Faculty;
import edu.miu.attendance.domain.Session;
import edu.miu.attendance.dto.AttendanceRecordDTO;
import edu.miu.attendance.dto.CourseOfferingDto;
import edu.miu.attendance.exception.ResourceNotFoundException;
import edu.miu.attendance.repository.CourseOfferingRepository;
import edu.miu.attendance.repository.CourseRepository;
import edu.miu.attendance.repository.FacultyRepository;
import edu.miu.attendance.repository.SessionRepository;
import edu.miu.attendance.utility.AttendanceRecordDTOMapper;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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
    private JdbcTemplate jdbcTemplate;

    public Page<CourseOfferingDto> findAll(Pageable pageable) {
        return courseOfferingRepository.findAll(pageable).map(courseOffering -> modelMapper.map(courseOffering, CourseOfferingDto.class));
    }

    public CourseOfferingDto findById(long id) {
        CourseOffering courseOffering=courseOfferingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("CourseOffering doesn't exit!"));
        return modelMapper.map(courseOffering, CourseOfferingDto.class);
    }

    @Transactional
    public CourseOfferingDto saveCourseOffering(CourseOfferingDto courseOfferingDto,Long courseOfferingId) {
        CourseOffering courseOffering;
        if(courseOfferingId!=null){
            courseOffering=courseOfferingRepository.findById(courseOfferingId).orElseThrow(() -> new ResourceNotFoundException("CourseOffering doesn't exit!"));
            courseOffering=modelMapper.map(courseOfferingDto, CourseOffering.class);
            courseOffering.setId(courseOfferingId);
        }else{
            courseOffering=modelMapper.map(courseOfferingDto, CourseOffering.class);
        }


        Course course=courseRepository.findById(courseOfferingDto.getCourse_id()).orElseThrow(() -> new ResourceNotFoundException("Course doesn't exit!"));
        Faculty faculty= facultyRepository.findById(courseOfferingDto.getFaculty_id()).orElseThrow(() -> new ResourceNotFoundException("Faculty doesn't exit!"));
        List<Session> sessions=sessionRepository.findAllById(courseOfferingDto.getSessions_id());
        if(sessions.isEmpty() || sessions.size()==0 || courseOfferingDto.getSessions_id().size()!=sessions.size()) {
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
        CourseOffering deletedData=courseOfferingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Data doesn't exit!"));
        courseOfferingRepository.deleteById(id);

        return modelMapper.map(deletedData, CourseOfferingDto.class);
    }

    @Override
    public List<AttendanceRecordDTO> attendanceExcelData(Long id) {
        String sql = "select p.firstName,p.lastName,s.studentid, fp.firstName As facultyFirstName,fp.lastName As facultyLastName,c.CourseCode,c.CourseName,c.department,c.credits,atd.ScanDateTime,l.name,lt.type  from CourseOffering cof \n" +
                "join AttendanceRecord atd\n" +
                "on cof.id = atd.courseOfferingId\n" +
                "join Faculty f\n" +
                "on cof.faculty_id =  f.id\n" +
                "join Person fp\n" +
                "on f.id = fp.id\n" +
                "join Student s\n" +
                "on atd.StudentId = s.id\n" +
                "join Person p\n" +
                "on s.id = p.id\n" +
                "join Location l\n" +
                "on atd.LocationId = l.id\n" +
                "join LocationType lt\n" +
                "on l.type_id = lt.id\n" +
                "join Course c\n" +
                "on cof.course_id =  c.id\n" +
                "where  cof.id = ?\n" +
                "order by atd.ScanDateTime asc;";
        return jdbcTemplate.query(sql, new Object[]{id}, new AttendanceRecordDTOMapper());
    }



    public List<CourseOfferingDto> findByDate(String date) {
        List<CourseOffering> resultData=courseOfferingRepository.findAllCourseOfferingByDate(LocalDate.parse(date));
        return resultData.stream().map(courseOffering -> modelMapper.map(courseOffering, CourseOfferingDto.class)).collect(Collectors.toList());

    }
}
