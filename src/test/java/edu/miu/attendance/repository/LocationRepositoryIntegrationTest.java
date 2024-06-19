package edu.miu.attendance.repository;

import edu.miu.attendance.domain.Location;
import edu.miu.attendance.domain.enums.LocationType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class LocationRepositoryIntegrationTest {

    @Autowired
    private LocationRepository locationRepository;

    @Test
    void whenFindById_thenReturnLocation() {
        // given
        Location location = getLocationEntity();
        locationRepository.save(location);

        // when
        Optional<Location> found = locationRepository.findById(location.getId());

        // then
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo(location.getName());
        assertThat(found.get().getLocationType().getType()).isEqualTo(location.getLocationType().getType());
    }

    @Test
    void whenDeleteById_thenLocationShouldBeDeleted() {
        // given
        Location location = getLocationEntity();
        locationRepository.save(location);

        // when
        locationRepository.deleteById(location.getId());
        Optional<Location> found = locationRepository.findById(location.getId());

        // then
        assertThat(found).isNotPresent();
    }

    private Location getLocationEntity() {
        Location location = new Location();
        location.setName("Library");

        LocationType locationType = new LocationType();
        locationType.setType("ON_CAMPUS");

        location.setLocationType(locationType);
        return location;
    }
}
