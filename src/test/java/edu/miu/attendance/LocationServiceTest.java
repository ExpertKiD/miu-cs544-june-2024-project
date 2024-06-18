package edu.miu.attendance;

import edu.miu.attendance.domain.AuditData;
import edu.miu.attendance.domain.Location;
import edu.miu.attendance.domain.enums.LocationType;
import edu.miu.attendance.dto.LocationDTO;
import edu.miu.attendance.repository.LocationRepository;
import edu.miu.attendance.repository.LocationTypeRepository;
import edu.miu.attendance.service.LocationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class LocationServiceTest {

    @InjectMocks
    private LocationServiceImpl locationService;

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private LocationTypeRepository locationTypeRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllLocations() {
        Location location = createLocation();
        Pageable pageable = PageRequest.of(0, 10);

        Page<Location> locationsPage = new PageImpl<>(Collections.singletonList(location));
        when(locationRepository.findAll(pageable)).thenReturn(locationsPage);

        assertFalse(locationService.getAllLocations(pageable).isEmpty());
        verify(locationRepository, times(1)).findAll();
    }

    @Test
    public void testGetLocationById() {
        Location location = createLocation();
        when(locationRepository.findById(1L)).thenReturn(Optional.of(location));

        LocationDTO locationDTO = locationService.getLocationById(1L);
        assertNotNull(locationDTO);
        assertEquals(location.getName(), locationDTO.getName());
        verify(locationRepository, times(1)).findById(1L);
    }

    @Test
    public void testCreateLocation() {
        Location location = createLocation();
        LocationDTO locationDTO = createLocationDTO();
        when(locationTypeRepository.findById(locationDTO.getLocationTypeId())).thenReturn(Optional.of(location.getLocationType()));
        when(locationRepository.save(any(Location.class))).thenReturn(location);

        LocationDTO createdLocation = locationService.createLocation(locationDTO);
        assertNotNull(createdLocation);
        assertEquals(location.getName(), createdLocation.getName());
        verify(locationRepository, times(1)).save(any(Location.class));
    }

    @Test
    public void testUpdateLocation() {
        Location location = createLocation();
        LocationDTO locationDTO = createLocationDTO();
        when(locationRepository.findById(1L)).thenReturn(Optional.of(location));
        when(locationTypeRepository.findById(locationDTO.getLocationTypeId())).thenReturn(Optional.of(location.getLocationType()));
        when(locationRepository.save(any(Location.class))).thenReturn(location);

        LocationDTO updatedLocation = locationService.updateLocation(1L, locationDTO);
        assertNotNull(updatedLocation);
        assertEquals(locationDTO.getName(), updatedLocation.getName());
        verify(locationRepository, times(1)).findById(1L);
        verify(locationRepository, times(1)).save(any(Location.class));
    }

    @Test
    public void testDeleteLocation() {
        doNothing().when(locationRepository).deleteById(1L);

        locationService.deleteLocation(1L);
        verify(locationRepository, times(1)).deleteById(1L);
    }

    private Location createLocation() {
        Location location = new Location();
        location.setId(1L);
        location.setName("Room 302");
        location.setCapacity(30);
        LocationType locationType = new LocationType();
        locationType.setId(1L);
        locationType.setType("Classroom");
        location.setLocationType(locationType);
        AuditData auditData = new AuditData();
        auditData.setCreatedOn(LocalDateTime.now());
        auditData.setUpdatedOn(LocalDateTime.now());
        auditData.setCreatedBy("admin");
        auditData.setUpdatedBy("admin");
        location.setAuditData(auditData);
        return location;
    }

    private LocationDTO createLocationDTO() {
        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setId(1L);
        locationDTO.setName("Room 101");
        locationDTO.setCapacity(30);
        locationDTO.setLocationTypeId(1L);
        AuditData auditData = new AuditData();
        auditData.setCreatedOn(LocalDateTime.now());
        auditData.setUpdatedOn(LocalDateTime.now());
        auditData.setCreatedBy("admin");
        auditData.setUpdatedBy("admin");
        return locationDTO;
    }
}
