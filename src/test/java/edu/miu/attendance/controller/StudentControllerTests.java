package edu.miu.attendance.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.miu.attendance.config.TestSecurityConfig;
import edu.miu.attendance.dto.StudentDTO;
import edu.miu.attendance.exception.ResourceNotFoundException;
import edu.miu.attendance.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestSecurityConfig.class)
class StudentControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    private StudentDTO existingStudentDTO;

    @BeforeEach
    public void setUp() {
        existingStudentDTO = new StudentDTO();
        existingStudentDTO.setStudentId("12345");
        existingStudentDTO.setFirstName("John");

        // Mock behavior for service methods
        when(studentService.getStudentByStudentId("12345")).thenReturn(existingStudentDTO);

        // Mock behavior for service methods throwing exceptions
        doThrow(new ResourceNotFoundException("Student not found")).when(studentService).updateStudent("12345", existingStudentDTO);
        doThrow(new ResourceNotFoundException("Student not found")).when(studentService).deleteStudentByStudentId("12345");
    }

    @Test
    void testSysAdmin_updateStudent_NotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/sys-admin" +
                                "/students/12345")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(existingStudentDTO)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testSysAdmin_deleteStudent_NotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/sys-admin/12345")
                )
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
