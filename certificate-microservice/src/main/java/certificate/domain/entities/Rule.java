package certificate.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Rule entity consisting of the eventId of the corresponding event and the index representing the hash of the rules
 * for that event.
 * This data is stored inside a DataBase and also uses the library lombok to autogenerate the getters and constructors.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "RuleHash")
public class Rule {

    @Id
    @Column(name = "eventId", nullable = false, unique = true)
    private long eventId;

    @Column(name = "ruleIndex")
    private int ruleIndex;
}
