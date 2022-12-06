package nl.tudelft.sem.template.user.domain.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.tudelft.sem.template.user.domain.enums.BoatType;
import nl.tudelft.sem.template.user.domain.enums.ExperienceLevel;

/**
 * Certificate entity consisting of an ID, boat type, experience level.
 */
@Entity
@Data
@NoArgsConstructor
public class Certificate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long certificateId;
    public BoatType boatType;
    public ExperienceLevel experienceLevel;
}
