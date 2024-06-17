package edu.miu.attendance.repository;

import edu.miu.attendance.domain.Student;
import edu.miu.attendance.enumType.GenderType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class StudentRepositoryIntegrationTest {

    @Autowired
    private StudentRepository studentRepository;

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
