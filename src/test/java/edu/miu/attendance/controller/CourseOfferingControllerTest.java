package edu.miu.attendance.controller;

import edu.miu.attendance.config.TestSecurityConfig;
import edu.miu.attendance.dto.CourseOfferingDto;
import edu.miu.attendance.service.CourseOfferingServiceImpl;
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
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestSecurityConfig.class)
public class CourseOfferingControllerTest {

    CourseOfferingDto courseOfferingDto;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CourseOfferingServiceImpl courseOfferingService;

    @BeforeEach
    public void setUp() {
        courseOfferingDto = new CourseOfferingDto();
        courseOfferingDto.setId(1L);
        courseOfferingDto.setCapacity(20);
        courseOfferingDto.setRoom("R-1");
    }

    @Test
    public void testGetCourseOfferingsById() throws Exception {
        Mockito.when(courseOfferingService.findById(anyLong())).thenReturn(courseOfferingDto);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/student-view/course-offerings/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.capacity", is(20)))
                .andExpect(jsonPath("$.room", is("R-1")));
    }

    @Test
    public void testGetAllCourseOfferings() throws Exception {

        Page<CourseOfferingDto> page = new PageImpl<>(Collections.singletonList(courseOfferingDto));
        Mockito.when(courseOfferingService.findAll(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/sys-admin/course-offerings")
                        .param("page", "0")
                        .param("size", "10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id", is(1)))
                .andExpect(jsonPath("$.content[0].capacity", is(20)))
                .andExpect(jsonPath("$.content[0].room", is("R-1")));
    }


    @Test
    @WithMockUser(username = "psalek", password = "qwerty", roles = "ADMIN")
    public void testCreateCourseOffering() throws Exception {

        Mockito.when(courseOfferingService.saveCourseOffering(eq("psalek"), any(CourseOfferingDto.class), any())).thenReturn(courseOfferingDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/sys-admin/course-offerings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"capacity\": 20, \"room\": \"R-1\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.capacity", is(20)))
                .andExpect(jsonPath("$.room", is("R-1")));
    }


    @Test
    @WithMockUser(username = "psalek", password = "qwerty", roles = "ADMIN")
    public void testUpdateCourseOffering() throws Exception {

        Mockito.when(courseOfferingService.saveCourseOffering(eq("psalek"), any(CourseOfferingDto.class), anyLong())).thenReturn(courseOfferingDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/sys-admin/course-offerings/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"capacity\": 20, \"room\": \"R-1\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.capacity", is(20)))
                .andExpect(jsonPath("$.room", is("R-1")));
    }

    /*
    @Test
    @WithMockUser(username = "psalek", password = "qwerty", roles = "ADMIN")
    public void testDeleteCourseOffering() throws Exception {

        Mockito.when(courseOfferingService.deleteCourseOffering(eq("psalek"),1L )).thenReturn(courseOfferingDto);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/sys-admin/course-offerings/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.capacity", is(20)))
                .andExpect(jsonPath("$.room", is("R-1")));
    }

     */
}
