package nl.tudelft.sem.template.authentication.foreignDomain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
