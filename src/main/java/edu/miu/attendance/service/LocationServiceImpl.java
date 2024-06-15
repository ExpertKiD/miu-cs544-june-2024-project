package edu.miu.attendance.service.impl;

import edu.miu.attendance.domain.Location;
import edu.miu.attendance.dto.LocationDTO;
import edu.miu.attendance.repository.LocationRepository;
import edu.miu.attendance.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Override
    public LocationDTO createLocation(LocationDTO locationDTO) {
        Location location = convertToEntity(locationDTO);
        Location savedLocation = locationRepository.save(location);
        return convertToDTO(savedLocation);
    }

    @Override
    public List<LocationDTO> getAllLocations() {
        List<Location> locations = locationRepository.findAll();
        return locations.stream().map(this::convertToDTO).toList();
    }

    @Override
    public LocationDTO getLocationById(Long id) {
        Optional<Location> location = locationRepository.findById(id);
        return location.map(this::convertToDTO).orElseThrow(() -> new RuntimeException("Location not found with id " + id));
    }

    @Override
    public LocationDTO updateLocation(Long id, LocationDTO locationDTO) {
        Location location = convertToEntity(locationDTO);
        location.setId(id);
        Location updatedLocation = locationRepository.save(location);
        return convertToDTO(updatedLocation);
    }

    @Override
    public void deleteLocation(Long id) {
        Location location = locationRepository.findById(id).orElseThrow(() -> new RuntimeException("Location not found with id " + id));
        locationRepository.delete(location);
    }

    private Location convertToEntity(LocationDTO locationDTO) {
        Location location = new Location();
        location.setId(locationDTO.getId());
        location.setName(locationDTO.getName());
        location.setCapacity(locationDTO.getCapacity());
        location.setLocationType(locationDTO.getLocationType());
        location.setAuditData(locationDTO.getAuditData());
        return location;
    }

    private LocationDTO convertToDTO(Location location) {
        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setId(location.getId());
        locationDTO.setName(location.getName());
        locationDTO.setCapacity(location.getCapacity());
        locationDTO.setLocationType(location.getLocationType());
        locationDTO.setAuditData(location.getAuditData());
        return locationDTO;
    }
}
