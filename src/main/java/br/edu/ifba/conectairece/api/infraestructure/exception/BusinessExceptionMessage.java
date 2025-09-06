package br.edu.ifba.conectairece.api.infraestructure.exception;

/**
 * Enum that defines business exception messages.
 *
 * @author Jorge Roberto
 */
public enum BusinessExceptionMessage {

    // =========================================================== //
    // =============== [        ATTRIBUTES       ] ================ //
    // =========================================================== //

    NOT_FOUND("The requested instance does not exist in the database."),
    INVALID_DATE("The provided date is not valid."),
    ATTRIBUTE_VALUE_ALREADY_EXISTS("The value of attribute is already in use."),
    CLASS_IN_USE("Cannot be deleted. It is being used by one or more related classes."),
    INVALID_CREDENTIALS("Your credentials are invalid");

    private final String message;

    // =========================================================== //
    // =============== [        CONSTRUCTOR      ] ================ //
    // =========================================================== //
    BusinessExceptionMessage(String message) {
        this.message = message;
    }

    // =========================================================== //
    // ================ [        METHODS         ] ================ //
    // =========================================================== //

    /**
     * Returns the enum message.
     *
     * @return the selected enum message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Returns a customized message for cases where
     * the attribute value already exists in the database.
     *
     * @param attribute The attribute whose value already exists and cannot be registered again.
     * @return a customized error message.
     */
    public String getAttributeValueAlreadyExistsMessage(String attribute) {
        return String.format(message, attribute);
    }
}
