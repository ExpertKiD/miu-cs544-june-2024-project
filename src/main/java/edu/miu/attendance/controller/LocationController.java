package edu.miu.attendance.controller;

import edu.miu.attendance.dto.LocationDTO;
import edu.miu.attendance.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sys-admin/locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @PostMapping
    public ResponseEntity<LocationDTO> createLocation(@RequestBody LocationDTO locationDTO) {
        LocationDTO createdLocation = locationService.createLocation(locationDTO);
        return ResponseEntity.ok(createdLocation);
    }

    @GetMapping
    public ResponseEntity<List<LocationDTO>> getAllLocations() {
        List<LocationDTO> locations = locationService.getAllLocations();
        return ResponseEntity.ok(locations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationDTO> getLocationById(@PathVariable Long id) {
        LocationDTO location = locationService.getLocationById(id);
        return ResponseEntity.ok(location);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LocationDTO> updateLocation(@PathVariable Long id, @RequestBody LocationDTO locationDTO) {
        LocationDTO updatedLocation = locationService.updateLocation(id, locationDTO);
        return ResponseEntity.ok(updatedLocation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
        locationService.deleteLocation(id);
        return ResponseEntity.noContent().build();
    }
}
