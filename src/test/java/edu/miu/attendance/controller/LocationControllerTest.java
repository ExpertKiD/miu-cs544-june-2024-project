package edu.miu.attendance.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.miu.attendance.config.TestSecurityConfig;
import edu.miu.attendance.dto.LocationDTO;
import edu.miu.attendance.exception.ResourceNotFoundException;
import edu.miu.attendance.service.LocationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestSecurityConfig.class)
class LocationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LocationService locationService;

    private LocationDTO existingLocationDTO;

    @BeforeEach
    public void setUp() {
        existingLocationDTO = new LocationDTO();
        existingLocationDTO.setId(1L);
        existingLocationDTO.setName("Test Location");
        existingLocationDTO.setCapacity(100);

        // Mock behavior for service methods
        Pageable pageable = PageRequest.of(0, 10);
        Page<LocationDTO> locations = new PageImpl<>(Collections.singletonList(existingLocationDTO), pageable, 1);

        when(locationService.getAllLocations(any(Pageable.class))).thenReturn(locations);
        when(locationService.getLocationById(1L)).thenReturn(existingLocationDTO);
        when(locationService.createLocation(any(LocationDTO.class))).thenReturn(existingLocationDTO);
        when(locationService.updateLocation(eq(1L), any(LocationDTO.class))).thenReturn(existingLocationDTO);

        // Mock behavior for service methods throwing exceptions
        doThrow(new ResourceNotFoundException("Location not found")).when(locationService).getLocationById(2L);
        doThrow(new ResourceNotFoundException("Location not found")).when(locationService).updateLocation(eq(2L), any(LocationDTO.class));
        doThrow(new ResourceNotFoundException("Location not found")).when(locationService).deleteLocation(2L);
    }

    @Test
    void testGetAllLocations() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/sys-admin/locations")
                        .with(httpBasic("psalek", "qwerty"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].name").value("Test Location"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].capacity").value(100));

        verify(locationService, times(1)).getAllLocations(any(Pageable.class));
    }

    @Test
    void testGetLocationById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/sys-admin/locations/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Location"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.capacity").value(100));

        verify(locationService, times(1)).getLocationById(1L);
    }

    @Test
    void testGetLocationById_NotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/sys-admin/locations/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(locationService, times(1)).getLocationById(2L);
    }

    @Test
    void testCreateLocation() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/sys-admin/locations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(existingLocationDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Location"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.capacity").value(100));

        verify(locationService, times(1)).createLocation(any(LocationDTO.class));
    }

    @Test
    void testUpdateLocation() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/sys-admin/locations/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(existingLocationDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Location"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.capacity").value(100));

        verify(locationService, times(1)).updateLocation(eq(1L), any(LocationDTO.class));
    }

    @Test
    void testUpdateLocation_NotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/sys-admin/locations/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(existingLocationDTO)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(locationService, times(1)).updateLocation(eq(2L), any(LocationDTO.class));
    }

    @Test
    void testDeleteLocation() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/sys-admin/locations/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(locationService, times(1)).deleteLocation(1L);
    }

    @Test
    void testDeleteLocation_NotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/sys-admin/locations/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(locationService, times(1)).deleteLocation(2L);
    }
}
