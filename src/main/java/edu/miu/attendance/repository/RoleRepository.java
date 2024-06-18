package edu.miu.attendance.repository;

import edu.miu.attendance.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
