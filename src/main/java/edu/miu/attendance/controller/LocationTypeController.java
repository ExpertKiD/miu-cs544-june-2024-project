package edu.miu.attendance.controller;

import edu.miu.attendance.dto.LocationTypeDTO;
import edu.miu.attendance.service.LocationTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sys-admin/location-types")
public class LocationTypeController {

    @Autowired
    private LocationTypeService locationTypeService;

    @GetMapping
    public List<LocationTypeDTO> getAllLocationTypes() {
        return locationTypeService.getAllLocationTypes();
    }

    @GetMapping("/{id}")
    public LocationTypeDTO getLocationTypeById(@PathVariable Long id) {
        return locationTypeService.getLocationTypeById(id);
    }

    @PostMapping
    public LocationTypeDTO createLocationType(@RequestBody LocationTypeDTO locationTypeDTO) {
        return locationTypeService.createLocationType(locationTypeDTO);
    }

    @PutMapping("/{id}")
    public LocationTypeDTO updateLocationType(@PathVariable Long id, @RequestBody LocationTypeDTO locationTypeDTO) {
        return locationTypeService.updateLocationType(id, locationTypeDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteLocationType(@PathVariable Long id) {
        locationTypeService.deleteLocationType(id);
    }
}
