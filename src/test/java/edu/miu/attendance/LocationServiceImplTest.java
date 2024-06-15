package edu.miu.attendance;

import edu.miu.attendance.domain.Location;
import edu.miu.attendance.dto.LocationDTO;
import edu.miu.attendance.repository.LocationRepository;
import edu.miu.attendance.service.LocationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import edu.miu.attendance.service.impl.LocationServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LocationServiceImplTest {

    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private LocationServiceImpl locationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateLocation() {
        Location location = new Location();
        location.setId(1L);
        location.setName("Test Location");
        location.setCapacity(100);

        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setName("Test Location");
        locationDTO.setCapacity(100);

        when(locationRepository.save(any(Location.class))).thenReturn(location);

        LocationDTO createdLocation = locationService.createLocation(locationDTO);

        assertNotNull(createdLocation);
        assertEquals("Test Location", createdLocation.getName());
        assertEquals(100, createdLocation.getCapacity());
    }

    @Test
    void testGetAllLocations() {
        List<Location> locations = new ArrayList<>();
        Location location = new Location();
        location.setId(1L);
        location.setName("Test Location");
        location.setCapacity(100);
        locations.add(location);

        when(locationRepository.findAll()).thenReturn(locations);

        List<LocationDTO> locationDTOs = locationService.getAllLocations();

        assertNotNull(locationDTOs);
        assertEquals(1, locationDTOs.size());
        assertEquals("Test Location", locationDTOs.get(0).getName());
    }

    @Test
    void testGetLocationById() {
        Location location = new Location();
        location.setId(1L);
        location.setName("Test Location");
        location.setCapacity(100);

        when(locationRepository.findById(1L)).thenReturn(Optional.of(location));

        LocationDTO locationDTO = locationService.getLocationById(1L);

        assertNotNull(locationDTO);
        assertEquals("Test Location", locationDTO.getName());
        assertEquals(100, locationDTO.getCapacity());
    }

    @Test
    void testUpdateLocation() {
        Location location = new Location();
        location.setId(1L);
        location.setName("Updated Location");
        location.setCapacity(200);

        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setName("Updated Location");
        locationDTO.setCapacity(200);

        when(locationRepository.findById(1L)).thenReturn(Optional.of(location));
        when(locationRepository.save(any(Location.class))).thenReturn(location);

        LocationDTO updatedLocation = locationService.updateLocation(1L, locationDTO);

        assertNotNull(updatedLocation);
        assertEquals("Updated Location", updatedLocation.getName());
        assertEquals(200, updatedLocation.getCapacity());
    }

    @Test
    void testDeleteLocation() {
        Location location = new Location();
        location.setId(1L);
        location.setName("Test Location");
        location.setCapacity(100);

        when(locationRepository.findById(1L)).thenReturn(Optional.of(location));
        doNothing().when(locationRepository).delete(location);

        locationService.deleteLocation(1L);

        verify(locationRepository, times(1)).delete(location);
    }
}
