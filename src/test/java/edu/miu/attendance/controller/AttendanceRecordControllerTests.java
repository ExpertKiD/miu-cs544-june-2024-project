package edu.miu.attendance.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.miu.attendance.dto.AttendanceRecordDTO;
import edu.miu.attendance.service.AttendanceRecordService;
import edu.miu.attendance.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class AttendanceRecordControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AttendanceRecordService attendanceRecordService;

    private AttendanceRecordDTO record1;
    private AttendanceRecordDTO record2;
    private Page<AttendanceRecordDTO> attendanceRecordsPage;

    @BeforeEach
    public void setUp() {
        record1 = new AttendanceRecordDTO();
        record1.setId(1L);
        record1.setLocationName("Main Hall");
        record1.setLocationType("Lecture");
        record1.setCourseOfferingName("Software Engineering");

        record2 = new AttendanceRecordDTO();
        record2.setId(2L);
        record2.setLocationName("Lab");
        record2.setLocationType("Practical");
        record2.setCourseOfferingName("Data Science");

        List<AttendanceRecordDTO> recordsList = Arrays.asList(record1, record2);
        attendanceRecordsPage = new PageImpl<>(recordsList, PageRequest.of(0, 10), recordsList.size());

        when(attendanceRecordService.getAttendanceRecordsForStudent(1L, PageRequest.of(0, 10)))
                .thenReturn(attendanceRecordsPage);
    }

    @Test
    void testGetAttendanceRecords() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/student-view/attendance-records")
                        .param("studentId", "1")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].locationName").value("Main Hall"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].locationType").value("Lecture"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].courseOfferingName").value("Software Engineering"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].locationName").value("Lab"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].locationType").value("Practical"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].courseOfferingName").value("Data Science"));
    }
}
