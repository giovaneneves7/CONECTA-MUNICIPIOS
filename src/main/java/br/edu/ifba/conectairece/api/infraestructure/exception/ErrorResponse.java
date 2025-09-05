package br.edu.ifba.conectairece.api.infraestructure.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class that generates error messages when an exception is thrown
 * during an HTTP request.
 *
 * @author Jorge Roberto
 */
@Data
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    /**
     * The HTTP request status code.
     */
    private final int status;

    /**
     * The error message, passed in the exception instance.
     */
    private final String message;

    /**
     * The stack trace until the origin of the error.
     */
    private String stacktrace;

    /**
     * Validation errors.
     */
    private List<ValidationError> errors;

    /**
     * Static class that contains the field and the error message.
     */
    @Data
    @RequiredArgsConstructor
    public static class ValidationError {

        private final String field;
        private final String message;

    }

    /**
     * Adds a new validation error.
     *
     * @param field   The field where the error occurred.
     * @param message The error message.
     */
    public void addValidationError(final String field, final String message) {

        if (Objects.isNull(errors)) {
            this.errors = new ArrayList<>();
        }

        this.errors.add(new ValidationError(field, message));
    }

    /**
     * Generates a JSON with an error message for the HTTP request
     * based on the data of this instance.
     *
     * @return an error message in JSON format.
     */
    public String toJson() {
        return "{\"Status\": "
                .concat(String.valueOf(this.getStatus()))
                .concat(", ")
                .concat("\"message\": \"")
                .concat(this.getMessage())
                .concat("\"}");
    }
}
