package edu.miu.attendance.controller;

import static org.assertj.core.api.Assertions.assertThat;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.miu.attendance.dto.CourseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CourseIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/api/v1/courses";
    }


    @Test
    @WithMockUser
    void testAddCourse() throws Exception {
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setName("AI");
        courseDTO.setCode("CS507");
        courseDTO.setCredits(4);
        courseDTO.setDepartment("Compro");
        courseDTO.setDescription("Basic Artificial Intelligence");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(courseDTO), headers);

        ResponseEntity<CourseDTO> response = restTemplate.postForEntity(baseUrl, request, CourseDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("AI");
    }



    @Test
    @WithMockUser
    void testGetSingleCourse() {
        // Assuming the course with ID 1 exists
        ResponseEntity<CourseDTO> response = restTemplate.getForEntity(baseUrl + "/1", CourseDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("AI");
    }

    @Test
    @WithMockUser
    void testGetAllCourses() {
        ResponseEntity<CourseDTO[]> response = restTemplate.getForEntity(baseUrl, CourseDTO[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    @WithMockUser
    void testUpdateCourse() throws Exception {
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setId(1L);
        courseDTO.setName("Advanced AI");
        courseDTO.setCode("CS508");
        courseDTO.setCredits(4);
        courseDTO.setDepartment("Compro");
        courseDTO.setDescription("Advanced Artificial Intelligence");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(courseDTO), headers);

        ResponseEntity<CourseDTO> response = restTemplate.exchange(baseUrl, HttpMethod.PUT, request, CourseDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Advanced AI");
    }

    @Test
    @WithMockUser
    void testDeleteCourse() {
        // Assuming the course with ID 1 exists
        ResponseEntity<String> response = restTemplate.exchange(baseUrl + "/1", HttpMethod.DELETE, null, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Course deleted successfully");
    }

}
