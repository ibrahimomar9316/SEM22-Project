package certificate.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Certificate (Not to be confused with the certificate a user can have) entity consisting of the netId of the user
 * and the index representing the hash of the preferences for that user.
 * This data is stored inside a DataBase and also uses the library lombok to autogenerate the getters and constructors.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CertificateHash")
public class Certificate {

    @Id
    @Column(name = "netId", nullable = false, unique = true)
    private String netId;

    @Column(name = "certificateIndex")
    private int certificateIndex;
}
