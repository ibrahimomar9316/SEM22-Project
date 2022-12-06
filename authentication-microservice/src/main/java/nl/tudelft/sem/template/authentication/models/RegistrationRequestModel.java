package nl.tudelft.sem.template.authentication.models;

import lombok.Data;

import java.util.List;

/**
 * Model representing a registration request.
 */
@Data
public class RegistrationRequestModel {
    private String netId;
    private String password;
}