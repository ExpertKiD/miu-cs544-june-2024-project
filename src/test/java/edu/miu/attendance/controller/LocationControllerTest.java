package edu.miu.attendance.controller;

import edu.miu.attendance.config.TestSecurityConfig;
import edu.miu.attendance.dto.LocationDTO;
import edu.miu.attendance.service.LocationService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestSecurityConfig.class)
public class LocationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LocationService locationService;

    private LocationDTO locationDTO;

    @BeforeEach
    public void setUp() {
        locationDTO = new LocationDTO();
        locationDTO.setId(1L);
        locationDTO.setName("Main Hall");
        locationDTO.setLocationTypeId(1L); // Set the locationTypeId
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testGetAllLocations() throws Exception {
        Page<LocationDTO> page = new PageImpl<>(Collections.singletonList(locationDTO));
        Mockito.when(locationService.getAllLocations(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/sys-admin/locations")
                        .param("page", "0")
                        .param("size", "10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id", is(1)))
                .andExpect(jsonPath("$.content[0].name", is("Main Hall")))
                .andExpect(jsonPath("$.content[0].locationTypeId", is(1)));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testGetLocationById() throws Exception {
        Mockito.when(locationService.getLocationById(anyLong())).thenReturn(locationDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/sys-admin/locations/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Main Hall")))
                .andExpect(jsonPath("$.locationTypeId", is(1)));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testCreateLocation() throws Exception {
        Mockito.when(locationService.createLocation(any(LocationDTO.class))).thenReturn(locationDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/sys-admin/locations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Main Hall\", \"locationTypeId\": 1}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Main Hall")))
                .andExpect(jsonPath("$.locationTypeId", is(1)));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testUpdateLocation() throws Exception {
        Mockito.when(locationService.updateLocation(anyLong(), any(LocationDTO.class))).thenReturn(locationDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/sys-admin/locations/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Main Hall\", \"locationTypeId\": 1}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Main Hall")))
                .andExpect(jsonPath("$.locationTypeId", is(1)));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testDeleteLocation() throws Exception {
        Mockito.doNothing().when(locationService).deleteLocation(anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/sys-admin/locations/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
