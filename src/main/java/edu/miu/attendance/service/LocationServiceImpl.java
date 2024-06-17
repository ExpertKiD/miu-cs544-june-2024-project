package edu.miu.attendance.service;

import edu.miu.attendance.domain.Location;
import edu.miu.attendance.domain.LocationType;
import edu.miu.attendance.dto.LocationDTO;
import edu.miu.attendance.repository.LocationRepository;
import edu.miu.attendance.repository.LocationTypeRepository;
import edu.miu.attendance.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private LocationTypeRepository locationTypeRepository;

    @Override
    public Page<LocationDTO> getAllLocations(Pageable pageable) {
        return locationRepository.findAll(pageable)
                .map(this::convertToDTO);
    }

    @Override
    public LocationDTO getLocationById(Long id) {
        return locationRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Location not found"));
    }

    @Override
    public LocationDTO createLocation(LocationDTO locationDTO) {
        Location location = convertToEntity(locationDTO);
        return convertToDTO(locationRepository.save(location));
    }

    @Override
    public LocationDTO updateLocation(Long id, LocationDTO locationDTO) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Location not found"));
        location.setName(locationDTO.getName());
        location.setCapacity(locationDTO.getCapacity());
        LocationType locationType = locationTypeRepository.findById(locationDTO.getLocationTypeId())
                .orElseThrow(() -> new RuntimeException("LocationType not found"));
        location.setLocationType(locationType);
        return convertToDTO(locationRepository.save(location));
    }

    @Override
    public void deleteLocation(Long id) {
        locationRepository.deleteById(id);
    }

    private LocationDTO convertToDTO(Location location) {
        LocationDTO dto = new LocationDTO();
        dto.setId(location.getId());
        dto.setName(location.getName());
        dto.setCapacity(location.getCapacity());
        dto.setLocationTypeId(location.getLocationType().getId());
        return dto;
    }

    private Location convertToEntity(LocationDTO dto) {
        Location location = new Location();
        location.setName(dto.getName());
        location.setCapacity(dto.getCapacity());
        LocationType locationType = locationTypeRepository.findById(dto.getLocationTypeId())
                .orElseThrow(() -> new RuntimeException("LocationType not found"));
        location.setLocationType(locationType);
        return location;
    }
}
