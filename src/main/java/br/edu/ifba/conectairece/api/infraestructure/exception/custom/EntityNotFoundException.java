package br.edu.ifba.conectairece.api.infraestructure.exception.custom;

import lombok.Getter;

/**
 * Exception thrown when a requested entity is not found in the system.
 *
 * <p>Can include metadata about the resource type and its identifier code.</p>
 *
 * @author Jorge Roberto
 */
@Getter
public class EntityNotFoundException extends RuntimeException {

    /** Resource name that was not found. */
    private String recourse;

    /** Identifier code for the missing entity. */
    private String code;

    /**
     * Constructs an exception with resource name and identifier code.
     *
     * @param recourse the entity name
     * @param code the entity identifier
     */
    public EntityNotFoundException(String recourse, String code) {
        this.recourse = recourse;
        this.code = code;
    }

    /**
     * Constructs an exception with a descriptive message.
     *
     * @param message the exception message
     */
    public EntityNotFoundException(String message) {
        super(message);
    }
}
