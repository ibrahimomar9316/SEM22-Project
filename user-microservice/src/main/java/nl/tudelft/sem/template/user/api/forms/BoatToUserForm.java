package nl.tudelft.sem.template.user.api.forms;

import lombok.Data;

/**
 * Form for correct input for POST mapping when connecting boat to the user
 */
@Data
public class BoatToUserForm {
    private Long appUserId;
    private Long boatId;
}
