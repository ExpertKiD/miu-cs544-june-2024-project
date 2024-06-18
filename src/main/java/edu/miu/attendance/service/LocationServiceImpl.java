package edu.miu.attendance.service;

import edu.miu.attendance.domain.Location;
import edu.miu.attendance.domain.LocationType;
import edu.miu.attendance.dto.LocationDTO;
import edu.miu.attendance.exception.ResourceNotFoundException;
import edu.miu.attendance.repository.LocationRepository;
import edu.miu.attendance.repository.LocationTypeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private LocationTypeRepository locationTypeRepository;

    @Autowired
    private ModelMapper modelMapper;

    private static final String LOCATION_NOT_FOUND_MESSAGE = "Location not found";

    @Override
    public Page<LocationDTO> getAllLocations(Pageable pageable) {
        return locationRepository.findAll(pageable)
                .map(location -> modelMapper.map(location, LocationDTO.class));
    }

    @Override
    public LocationDTO getLocationById(Long id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(LOCATION_NOT_FOUND_MESSAGE));
        return modelMapper.map(location, LocationDTO.class);
    }

    @Override
    @Transactional
    public LocationDTO createLocation(LocationDTO locationDTO) {
        Location location = modelMapper.map(locationDTO, Location.class);
        LocationType locationType = locationTypeRepository.findById(locationDTO.getLocationTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("LocationType not found"));
        location.setLocationType(locationType);
        location = locationRepository.save(location);
        return modelMapper.map(location, LocationDTO.class);
    }

    @Override
    @Transactional
    public LocationDTO updateLocation(Long id, LocationDTO locationDTO) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(LOCATION_NOT_FOUND_MESSAGE));
        modelMapper.map(locationDTO, location);
        LocationType locationType = locationTypeRepository.findById(locationDTO.getLocationTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("LocationType not found"));
        location.setLocationType(locationType);
        location = locationRepository.save(location);
        return modelMapper.map(location, LocationDTO.class);
    }
    @Override
    @Transactional
    public void deleteLocation(Long id) {
        if (!locationRepository.existsById(id)) {
            throw new ResourceNotFoundException(LOCATION_NOT_FOUND_MESSAGE);
        }
        locationRepository.deleteById(id);
    }
}
