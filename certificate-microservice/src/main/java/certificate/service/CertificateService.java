package certificate.service;

import certificate.domain.CertificateRepository;
import certificate.domain.entities.Certificate;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * The type Certificate service.
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CertificateService {
    private final CertificateRepository certificateRepository;

    /**
     * Generate id int.
     *
     * @param isMale        the is male
     * @param isCompetitive the is competitive
     * @param position      the position
     * @param certificate   the certificate
     * @return the int
     */
    public int generateId(boolean isMale, boolean isCompetitive, String position, String certificate) {
        int id = 0;
        if (isMale) {
            id += 1;
        } else {
            id += 2;
        }
        id *= 10;
        if (isCompetitive) {
            id += 1;
        }
        id *= 10;
        if (position.contains("COX")) {
            switch (certificate) {
                case "C4":
                    id += 1;
                    break;
                case "FOURPLUS":
                    id += 2;
                    break;
                case "EIGHTPLUS":
                    id += 3;
                    break;
                default:
                    id *= 0;
                    break;
            }
        }
        return id;
    }

    /**
     * Save certificate certificate.
     *
     * @param certificate the certificate
     */
    public void saveCertificate(Certificate certificate) {
        certificateRepository.save(certificate);
    }

    /**
     * Gets all certificates.
     *
     * @return the all certificates
     */
    public List<Certificate> getAllCertificates() {
        return certificateRepository.findAll();
    }
}
