package edu.miu.attendance;

import edu.miu.attendance.dto.LocationDTO;
import edu.miu.attendance.domain.Location;
import edu.miu.attendance.domain.enums.LocationType;
import edu.miu.attendance.exception.ResourceNotFoundException;
import edu.miu.attendance.repository.LocationRepository;
import edu.miu.attendance.service.LocationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LocationServiceTest {

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private LocationServiceImpl locationService;

    private Location location;
    private LocationDTO locationDTO;

    @BeforeEach
    void setUp() {
        location = new Location();
        location.setId(1L);
        location.setName("Library");
        LocationType locationType = new LocationType();
        locationType.setType("ON_CAMPUS");
        location.setLocationType(locationType);

        locationDTO = new LocationDTO();
        locationDTO.setId(1L);
        locationDTO.setName("Library");
        locationDTO.setLocationTypeId(1L);
    }



    @Test
    void whenGetAllLocations_thenLocationsShouldBeReturned() {
        List<Location> locations = Arrays.asList(location);
        Page<Location> page = new PageImpl<>(locations);
        Pageable pageable = PageRequest.of(0, 10);

        when(locationRepository.findAll(pageable)).thenReturn(page);
        when(modelMapper.map(any(Location.class), any())).thenReturn(locationDTO);

        Page<LocationDTO> result = locationService.getAllLocations(pageable);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getName()).isEqualTo(locationDTO.getName());
    }

    @Test
    void whenGetLocationById_thenLocationShouldBeReturned() {
        when(locationRepository.findById(1L)).thenReturn(Optional.of(location));
        when(modelMapper.map(any(Location.class), any())).thenReturn(locationDTO);

        LocationDTO foundLocationDTO = locationService.getLocationById(1L);

        assertThat(foundLocationDTO).isNotNull();
        assertThat(foundLocationDTO.getName()).isEqualTo(locationDTO.getName());
    }


    @Test
    void whenGetLocationById_withInvalidId_thenThrowException() {
        when(locationRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            locationService.getLocationById(1L);
        });
    }
}
