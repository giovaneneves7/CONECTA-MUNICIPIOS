package br.edu.ifba.conectairece.api.infraestructure.exception.custom;

/**
 * Exception thrown when a provided password is invalid
 * (e.g., wrong credentials or format violation).
 *
 * @author Jorge Roberto
 */
public class PasswordInvalidException extends RuntimeException {
    /**
     * Constructs the exception with a specific message.
     *
     * @param message the exception message
     */
    public PasswordInvalidException(String message) {
        super(message);
    }
}
