package edu.miu.attendance.service;
import edu.miu.attendance.dto.LocationDTO;

import java.util.List;

public interface LocationService {
    LocationDTO createLocation(LocationDTO locationDTO);
    List<LocationDTO> getAllLocations();
    LocationDTO getLocationById(Long id);
    LocationDTO updateLocation(Long id, LocationDTO locationDTO);
    void deleteLocation(Long id);
}

