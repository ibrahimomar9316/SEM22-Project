package nl.tudelft.sem.template.user.api.forms;

import lombok.Data;

/**
 * Form for correct input for POST mapping when connecting role to the user.
 */
@Data
public class RoleToUserForm {
    private Long appUserId;
    private Long roleId;
}
