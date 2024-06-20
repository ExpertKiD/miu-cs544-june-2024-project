package edu.miu.attendance.controller;

import edu.miu.attendance.config.TestSecurityConfig;
import edu.miu.attendance.dto.AttendanceRecordDTO;
import edu.miu.attendance.dto.StudentDTO;
import edu.miu.attendance.service.AttendanceRecordService;
import edu.miu.attendance.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestSecurityConfig.class)
public class AttendanceRecordControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AttendanceRecordService attendanceRecordService;

    @MockBean
    private StudentService studentService;

    private AttendanceRecordDTO attendanceRecordDTO;
    private StudentDTO studentDTO;

    @BeforeEach
    public void setUp() {
        attendanceRecordDTO = new AttendanceRecordDTO();
        attendanceRecordDTO.setId(1L);
        attendanceRecordDTO.setCourseOfferingName("Software Engineering");
        attendanceRecordDTO.setLocationName("Main Hall");
        attendanceRecordDTO.setLocationType("Lecture");

        studentDTO = new StudentDTO();
        studentDTO.setStudentId("1");
        studentDTO.setFirstName("John");
        studentDTO.setLastName("Doe");
    }

    @Test
    @WithMockUser(username = "johndoe", password = "password", roles = "STUDENT")
    public void testGetAttendanceRecords() throws Exception {
        Page<AttendanceRecordDTO> page = new PageImpl<>(Collections.singletonList(attendanceRecordDTO));
        Mockito.when(studentService.getStudentByUsername(anyString())).thenReturn(studentDTO);
        Mockito.when(attendanceRecordService.getAttendanceRecordsForStudent(anyString(), any(Pageable.class))).thenReturn(page);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/student-view/attendance-records")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id", is(1)))
                .andExpect(jsonPath("$.content[0].courseOfferingName", is("Software Engineering")))
                .andExpect(jsonPath("$.content[0].locationName", is("Main Hall")))
                .andExpect(jsonPath("$.content[0].locationType", is("Lecture")));
    }
}
