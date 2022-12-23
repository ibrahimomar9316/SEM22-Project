package certificate.service;

import certificate.domain.CertificateRepository;
import certificate.domain.entities.Certificate;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * The service used to operate on the hashed Preferences database.
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CertificateService {
    private final CertificateRepository certificateRepository;

    /**
     * Function used to save the hashed preferences in the database.
     *
     * @param certificate the hashed preferences we want to store in the database
     */
    public void save(Certificate certificate) {
        certificateRepository.save(certificate);
    }

    /**
     * Getter function to retrieve all hashed preferences for all users that selected their preferences.
     *
     * @return all users with their corresponding hashed preferences
     */
    public List<Certificate> getAllCertificates() {
        return certificateRepository.findAll();
    }

    /**
     * Getter function to retrieve hashed preferences of the given user.
     *
     * @param netId netId of the user.
     *
     * @return Hashed preferences of the user.
     */
    public Certificate getCertificateBy(String netId) {
        return certificateRepository.getCertificateBy(netId);
    }

}
