package certificate.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
