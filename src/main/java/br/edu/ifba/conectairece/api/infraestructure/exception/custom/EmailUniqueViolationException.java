package br.edu.ifba.conectairece.api.infraestructure.exception.custom;

/**
 * Exception thrown when an Email already exists in the system,
 * violating the uniqueness constraint.
 *
 * @author Jorge Roberto
 */
public class EmailUniqueViolationException extends RuntimeException {
    /**
     * Constructs the exception with a specific message.
     *
     * @param message the exception message
     */
    public EmailUniqueViolationException(String message) {
        super(message);
    }
}
