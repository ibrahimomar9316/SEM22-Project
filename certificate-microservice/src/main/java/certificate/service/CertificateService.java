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
     * Save certificate certificate.
     *
     * @param certificate the certificate
     */
    public void save(Certificate certificate) {
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
