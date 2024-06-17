package edu.miu.attendance.service;

import edu.miu.attendance.domain.Student;
import edu.miu.attendance.dto.StudentDTO;
import edu.miu.attendance.exception.ResourceAlreadyExistsException;
import edu.miu.attendance.exception.ResourceNotFoundException;
import edu.miu.attendance.repository.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service()
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {
    private StudentRepository studentRepository;

    private ModelMapper modelMapper;


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
    @Transactional
    public void deleteStudentByStudentId(String studentId) {
        var student = studentRepository.findStudentByStudentId(studentId).orElseThrow(
                () -> new ResourceNotFoundException("Student with studentId #" + studentId + " doesn't exist")
        );

        studentRepository.deleteByStudentId(student.getStudentId());
    }
}
