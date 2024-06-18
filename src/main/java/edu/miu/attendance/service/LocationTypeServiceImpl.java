package edu.miu.attendance.service;

import edu.miu.attendance.domain.LocationType;
import edu.miu.attendance.dto.LocationTypeDTO;
import edu.miu.attendance.exception.ResourceNotFoundException;
import edu.miu.attendance.repository.LocationTypeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationTypeServiceImpl implements LocationTypeService {

    @Autowired
    private LocationTypeRepository locationTypeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<LocationTypeDTO> getAllLocationTypes() {
        return locationTypeRepository.findAll().stream()
                .map(locationType -> modelMapper.map(locationType, LocationTypeDTO.class))
                .toList();
    }

    @Override
    public LocationTypeDTO getLocationTypeById(Long id) {
        LocationType locationType = locationTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LocationType not found"));
        return modelMapper.map(locationType, LocationTypeDTO.class);
    }

    @Override
    @Transactional
    public LocationTypeDTO createLocationType(LocationTypeDTO locationTypeDTO) {
        LocationType locationType = modelMapper.map(locationTypeDTO, LocationType.class);
        locationType = locationTypeRepository.save(locationType);
        return modelMapper.map(locationType, LocationTypeDTO.class);
    }

    @Override
    @Transactional
    public LocationTypeDTO updateLocationType(Long id, LocationTypeDTO locationTypeDTO) {
        LocationType locationType = locationTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LocationType not found"));
        modelMapper.map(locationTypeDTO, locationType);
        locationType = locationTypeRepository.save(locationType);
        return modelMapper.map(locationType, LocationTypeDTO.class);
    }
    @Override
    @Transactional
    public void deleteLocationType(Long id) {
        if (!locationTypeRepository.existsById(id)) {
            throw new ResourceNotFoundException("LocationType not found");
        }
        locationTypeRepository.deleteById(id);
    }
}
