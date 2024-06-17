package edu.miu.attendance.seeder;

import edu.miu.attendance.domain.Location;
import edu.miu.attendance.domain.LocationType;
import edu.miu.attendance.repository.LocationRepository;
import edu.miu.attendance.repository.LocationTypeRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@Profile("dev")
public class Seeder {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private LocationTypeRepository locationTypeRepository;

    @PostConstruct
    public void seedDatabase(){
        addLocationTypes(locationTypeRepository);


        // Add Locations
        addLocations(locationRepository, locationTypeRepository);
    }

    private void addLocationTypes(LocationTypeRepository locationTypeRepository) {
        // Add Location types to database
        List<LocationType> locationTypes = new ArrayList<>(
                List.of(
                        new LocationType(null, "Online"),
                        new LocationType(null, "OnCampus")
                )
        );

        locationTypeRepository.saveAll(locationTypes);
    }

    private void addLocations(LocationRepository locationRepository, LocationTypeRepository locationTypeRepository) {
        // Get location types

        var locationTypes = locationTypeRepository.findAll();

        List<Location> locations = new ArrayList<>();

        Location l1 = new Location();

        l1.setCapacity(40);
        l1.setLocationType(locationTypes.get(1));
        l1.setName("McLaughin Building");

        Location l2 = new Location();

        l2.setCapacity(40);
        l2.setLocationType(locationTypes.get(1));
        l2.setName("Veril Hall");

        Location l3 = new Location();

        l3.setCapacity(100);
        l3.setLocationType(locationTypes.get(1));
        l3.setName("Argiro");

        Location l4 = new Location();

        l4.setCapacity(200);
        l4.setLocationType(locationTypes.get(0));
        l4.setName("Online");

        locations.add(l1);
        locations.add(l2);
        locations.add(l3);
        locations.add(l4);

        locationRepository.saveAll(locations);
    }

}
