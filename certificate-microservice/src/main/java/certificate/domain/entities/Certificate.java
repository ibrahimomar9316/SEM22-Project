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
@Table(name = "CertificateHash")
public class Certificate {

    @Id
    @Column(name = "netId", nullable = false, unique = true)
    private String netId;

    @Column(name = "certificateIndex")
    private int certificateIndex;
}
