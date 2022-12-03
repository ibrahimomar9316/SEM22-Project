package nl.tudelft.sem.template.user.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.tudelft.sem.template.user.domain.enums.RoleType;

import javax.persistence.*;

/**
 * Role entity consisting of an ID, role type
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long roleId;
    private RoleType roleType;
}
