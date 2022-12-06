package nl.tudelft.sem.template.user.domain.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.tudelft.sem.template.user.domain.enums.BoatType;

/**
 * Boat entity consisting of an ID, boat type.
 */
@Entity
@Data
@NoArgsConstructor
public class Boat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long boatId;
    private BoatType boatType;
}
