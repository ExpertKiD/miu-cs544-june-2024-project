package edu.miu.attendance.service;
import edu.miu.attendance.dto.LocationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LocationService {
    LocationDTO createLocation(LocationDTO locationDTO);
    Page<LocationDTO> getAllLocations(Pageable pageable);
    LocationDTO getLocationById(Long id);
    LocationDTO updateLocation(Long id, LocationDTO locationDTO);
    void deleteLocation(Long id);
}

