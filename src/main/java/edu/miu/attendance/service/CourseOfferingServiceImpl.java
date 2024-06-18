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
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Page<CourseOfferingDto> findAll(Pageable pageable) {
        return courseOfferingRepository.findAll(pageable)
                .map(courseOffering -> modelMapper.map(courseOffering, CourseOfferingDto.class));
    }

    public CourseOfferingDto findById(long id) {
        CourseOffering courseOffering=courseOfferingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Course doesn't exit!"));;
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
}
