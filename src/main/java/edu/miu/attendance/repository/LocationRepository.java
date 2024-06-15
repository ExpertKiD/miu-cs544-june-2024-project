package edu.miu.attendance.repository;

import edu.miu.attendance.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
