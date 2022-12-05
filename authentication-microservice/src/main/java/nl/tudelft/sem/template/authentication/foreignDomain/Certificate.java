package nl.tudelft.sem.template.authentication.foreignDomain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
