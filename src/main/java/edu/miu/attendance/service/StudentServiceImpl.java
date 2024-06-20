package edu.miu.attendance.service;

import edu.miu.attendance.domain.Student;
import edu.miu.attendance.dto.StudentCourseDTO;
import edu.miu.attendance.dto.CourseDTO;
import edu.miu.attendance.dto.StudentDTO;
import edu.miu.attendance.exception.ResourceAlreadyExistsException;
import edu.miu.attendance.exception.ResourceNotFoundException;
import edu.miu.attendance.repository.StudentRepository;
import edu.miu.attendance.utility.AttendanceRecordDTOMapper;
import edu.miu.attendance.utility.StudentCourseDTOMapper;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service()
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    @Transactional
    public StudentDTO addStudent(StudentDTO studentDTO) {
        boolean studentExists = studentRepository.findStudentByStudentId(studentDTO.getStudentId()).isPresent();
        if (studentExists) {
            throw new ResourceAlreadyExistsException("Student with id #" + studentDTO.getStudentId() + " already exists");
        }
        Student student = modelMapper.map(studentDTO, Student.class);
        student = studentRepository.save(student);
        return modelMapper.map(student, StudentDTO.class);
    }

    @Override
    public Page<StudentDTO> getAllStudents(Pageable pageable) {
        return studentRepository.findAll(pageable).map(student -> modelMapper.map(student, StudentDTO.class));
    }

    @Override
    public StudentDTO getStudentByStudentId(String studentId) {
        Student student = studentRepository.findStudentByStudentId(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student with studentId #" + studentId + " doesn't exist"));
        return modelMapper.map(student, StudentDTO.class);
    }

    @Override
    @Transactional
    public StudentDTO updateStudent(String studentId, StudentDTO studentDTO) {
        var student = studentRepository.findStudentByStudentId(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student with studentId #" + studentId + " doesn't exist"));

        modelMapper.map(studentDTO, student);

        return modelMapper.map(studentRepository.save(student), StudentDTO.class);
    }

    @Override
    public StudentDTO getStudentWithCourses(String studentId) {
        Student student = studentRepository.findStudentByStudentId(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student with studentId #" + studentId + " doesn't exist"));
        StudentDTO studentDTO = modelMapper.map(student, StudentDTO.class);

        // Converting CourseOfferings to CourseDTOs
        List<CourseDTO> courseDTOs = student.getCoursesRegistrations().stream()
                .map(courseOffering -> modelMapper.map(courseOffering.getCourse(), CourseDTO.class))
                .toList();
        studentDTO.setCourses(courseDTOs);
        return studentDTO;
    }

    @Override
    @Transactional
    public void deleteStudentByStudentId(String studentId) {
        var student = studentRepository.findStudentByStudentId(studentId).orElseThrow(
                () -> new ResourceNotFoundException("Student with studentId #" + studentId + " doesn't exist")
        );

        studentRepository.deleteByStudentId(student.getStudentId());
    }

    @Override
    public List<StudentCourseDTO> findCourseOfferingsByStudentId(Long studentId) {
        String sql = "select c.CourseCode,c.CourseName,c.CourseDescription,c.credits as CourseCredit,cof.grade from CourseRegistration cr \n" +
                "join CourseOffering cof\n" +
                "on cr.CourseOfferingId = cof.id\n" +
                "join Course c\n" +
                "on cof.course_id = c.id\n" +
                "where cr.StudentId =  ?";

        return jdbcTemplate.query(sql, new Object[]{studentId}, new StudentCourseDTOMapper());
    }



    @Override
    public StudentDTO getStudentByUsername(String username) {
        var student =
                studentRepository.findStudentByUsername(username).orElseThrow(
                        () -> new ResourceNotFoundException("Student with username #" + username + " doesn't exist")
                );

        return modelMapper.map(student, StudentDTO.class);

    }

    @Override
    public Optional<Student> studentByUsername(String username) {

        return studentRepository.findStudentByUsername(username);
    }

}
