package nl.tudelft.sem.template.user.api.forms;

import lombok.Data;

/**
 * Form for correct input for POST mapping when connecting certificate to the user
 */
@Data
public class CertificateToUserForm {
    private Long appUserId;
    private Long certificateId;
}