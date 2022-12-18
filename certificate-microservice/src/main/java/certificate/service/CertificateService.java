package certificate.service;

import certificate.domain.CertificateRepository;
import certificate.domain.entities.Certificate;
import java.util.List;
import javax.transaction.Transactional;

import certificate.foreigndomain.Gender;
import certificate.foreigndomain.Position;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CertificateService {
    private CertificateRepository certificateRepository;

    public CertificateService(CertificateRepository certificateRepository) {
        this.certificateRepository = certificateRepository;
    }

    private int generateId(String netId, boolean isMale, boolean isCompetitive, String position, String certificate) {
        return 0;
    }

    public Certificate saveCertificate(Certificate certificate) {
        return certificateRepository.save(certificate);
    }

    public List<Certificate> getAllCertificates() {
        return certificateRepository.findAll();
    }
}
