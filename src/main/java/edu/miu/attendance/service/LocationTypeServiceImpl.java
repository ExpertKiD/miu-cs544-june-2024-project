package edu.miu.attendance.service;

import edu.miu.attendance.domain.LocationType;
import edu.miu.attendance.dto.LocationTypeDTO;
import edu.miu.attendance.repository.LocationTypeRepository;
import edu.miu.attendance.service.LocationTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationTypeServiceImpl implements LocationTypeService {

    @Autowired
    private LocationTypeRepository locationTypeRepository;

    @Override
    public List<LocationTypeDTO> getAllLocationTypes() {
        return locationTypeRepository.findAll().stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public LocationTypeDTO getLocationTypeById(Long id) {
        return locationTypeRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("LocationType not found"));
    }

    @Override
    public LocationTypeDTO createLocationType(LocationTypeDTO locationTypeDTO) {
        LocationType locationType = convertToEntity(locationTypeDTO);
        return convertToDTO(locationTypeRepository.save(locationType));
    }

    @Override
    public LocationTypeDTO updateLocationType(Long id, LocationTypeDTO locationTypeDTO) {
        LocationType locationType = locationTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("LocationType not found"));
        locationType.setType(locationTypeDTO.getType());
        return convertToDTO(locationTypeRepository.save(locationType));
    }

    @Override
    public void deleteLocationType(Long id) {
        locationTypeRepository.deleteById(id);
    }

    private LocationTypeDTO convertToDTO(LocationType locationType) {
        LocationTypeDTO dto = new LocationTypeDTO();
        dto.setId(locationType.getId());
        dto.setType(locationType.getType());
        return dto;
    }

    private LocationType convertToEntity(LocationTypeDTO dto) {
        LocationType locationType = new LocationType();
        locationType.setType(dto.getType());
        return locationType;
    }
}
