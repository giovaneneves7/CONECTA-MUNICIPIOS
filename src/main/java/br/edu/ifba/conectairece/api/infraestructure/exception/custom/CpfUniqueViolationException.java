package br.edu.ifba.conectairece.api.infraestructure.exception.custom;

/**
 * Exception thrown when a CPF already exists in the system,
 * violating the uniqueness constraint.
 *
 * @author Jorge Roberto
 */
public class CpfUniqueViolationException extends RuntimeException {
    /**
     * Constructs the exception with a specific message.
     *
     * @param message the exception message
     */
    public CpfUniqueViolationException(String message) {
        super(message);
    }
}
