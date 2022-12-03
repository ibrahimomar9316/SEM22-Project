package nl.tudelft.sem.template.user.repositories;

import nl.tudelft.sem.template.user.domain.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Repository for the role entity
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
