package nl.tudelft.sem.template.user.domain.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import nl.tudelft.sem.template.user.domain.enums.BoatType;
import nl.tudelft.sem.template.user.domain.enums.ExperienceLevel;

import javax.persistence.*;

/**
 * Certificate entity consisting of an ID, boat type, experience level
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
