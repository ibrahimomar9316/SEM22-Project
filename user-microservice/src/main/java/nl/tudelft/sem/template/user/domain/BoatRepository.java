package nl.tudelft.sem.template.user.domain;

import nl.tudelft.sem.template.user.domain.entities.Boat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for the boat entity.
 */
@Repository
public interface BoatRepository extends JpaRepository<Boat, Long> {

}
