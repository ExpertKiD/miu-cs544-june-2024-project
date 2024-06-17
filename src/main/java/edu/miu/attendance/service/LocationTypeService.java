package edu.miu.attendance.service;

import edu.miu.attendance.dto.LocationTypeDTO;

import java.util.List;

public interface LocationTypeService {
    List<LocationTypeDTO> getAllLocationTypes();
    LocationTypeDTO getLocationTypeById(Long id);
    LocationTypeDTO createLocationType(LocationTypeDTO locationTypeDTO);
    LocationTypeDTO updateLocationType(Long id, LocationTypeDTO locationTypeDTO);
    void deleteLocationType(Long id);
}
