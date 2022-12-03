package nl.tudelft.sem.template.user.domain.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import nl.tudelft.sem.template.user.domain.enums.BoatType;

import javax.persistence.*;

/**
 * Boat entity consisting of an ID, boat type
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
