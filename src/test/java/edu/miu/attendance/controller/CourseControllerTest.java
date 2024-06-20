package edu.miu.attendance.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.miu.attendance.dto.CourseDTO;
import edu.miu.attendance.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@WebMvcTest(CourseController.class)
public class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    @Autowired
    private ObjectMapper objectMapper;

    private CourseDTO courseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        courseDTO = new CourseDTO();
        courseDTO.setId(1L);
        courseDTO.setName("SA");
        courseDTO.setCode("CS004");
        courseDTO.setCredits(4);
        courseDTO.setDepartment("Compro");
        courseDTO.setDescription("Software Architecture");
    }

    @Test
    @WithMockUser
    void testSaveCourse() throws Exception {
        when(courseService.addCourses(any(CourseDTO.class)))
                .thenReturn(new ResponseEntity(courseDTO, HttpStatus.OK));

        mockMvc.perform(post("/api/v1/courses")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(courseDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("SA"))
                .andExpect(jsonPath("$.code").value("CS004"));
    }

    @Test
    @WithMockUser
    void testGetSingleCourse() throws Exception {
        when(courseService.getById(anyLong()))
                .thenReturn(new ResponseEntity(courseDTO, HttpStatus.OK));

        mockMvc.perform(get("/api/v1/courses/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("SA"))
                .andExpect(jsonPath("$.code").value("CS004"));
    }

    @Test
    @WithMockUser
    void testGetAllCourses() throws Exception {
        when(courseService.getAllCourse())
                .thenReturn(new ResponseEntity(List.of(courseDTO), HttpStatus.OK));

        mockMvc.perform(get("/api/v1/courses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("SA"))
                .andExpect(jsonPath("$[0].code").value("CS004"));
    }

    @Test
    @WithMockUser
    void testUpdateCourse() throws Exception {
        when(courseService.updateCourse(any(CourseDTO.class)))
                .thenReturn(new ResponseEntity(courseDTO, HttpStatus.OK));

        mockMvc.perform(put("/api/v1/courses")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(courseDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("SA"))
                .andExpect(jsonPath("$.code").value("CS004"));
    }

    @Test
    @WithMockUser
    void testDeleteCourse() throws Exception {
        when(courseService.deleteCourse(anyLong()))
                .thenReturn(new ResponseEntity("Course deleted successfully", HttpStatus.OK));

        mockMvc.perform(delete("/api/v1/courses/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("Course deleted successfully"));
    }

}
