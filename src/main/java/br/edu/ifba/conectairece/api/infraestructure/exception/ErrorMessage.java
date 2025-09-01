package br.edu.ifba.conectairece.api.infraestructure.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.ToString;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Represents a standardized error response returned by the API.
 *
 * <p>This class encapsulates metadata such as HTTP status,
 * request path, method, and error messages. It may also include
 * field-specific validation errors when applicable.</p>
 *
 * @author Jorge Roberto
 */
@Getter
@ToString
public class ErrorMessage {

    /** Request path that triggered the error. */
    private String path;

    /** HTTP method of the request. */
    private String method;

    /** Numeric HTTP status code. */
    private int status;

    /** Textual representation of the HTTP status. */
    private String statusText;

    /** Human-readable error message. */
    private String message;

    /** Map of field-level validation errors. */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, String> errors;

    /** Default constructor. */
    public ErrorMessage() {}

    /**
     * Creates an {@link ErrorMessage} without validation details.
     *
     * @param request the HTTP request
     * @param status the HTTP status
     * @param message error description
     */
    public ErrorMessage(HttpServletRequest request, HttpStatus status, String message) {
        this.path = request.getRequestURI();
        this.method = request.getMethod();
        this.status = status.value();
        this.statusText = status.getReasonPhrase();
        this.message = message;
    }

    /**
     * Creates an {@link ErrorMessage} with validation details.
     *
     * @param request the HTTP request
     * @param status the HTTP status
     * @param message error description
     * @param result validation result
     */
    public ErrorMessage(HttpServletRequest request, HttpStatus status, String message, BindingResult result) {
        this(request, status, message);
        addErrors(result);
    }

    /**
     * Creates an {@link ErrorMessage} with localized validation details.
     *
     * @param request the HTTP request
     * @param status the HTTP status
     * @param message error description
     * @param result validation result
     * @param messageSource message source for localization
     */
    public ErrorMessage(HttpServletRequest request, HttpStatus status, String message, BindingResult result, MessageSource messageSource) {
        this(request, status, message);
        addErrors(result, messageSource, request.getLocale());
    }

    private void addErrors(BindingResult result, MessageSource messageSource, Locale locale) {
        this.errors = new HashMap<>();
        for (FieldError fieldError : result.getFieldErrors()) {
            String code = fieldError.getCodes()[0];
            String message = messageSource.getMessage(code, fieldError.getArguments(), locale);
            this.errors.put(fieldError.getField(), message);
        }
    }

    public void addErrors(BindingResult result) {
        this.errors = new HashMap<>();
        for (FieldError fieldError : result.getFieldErrors()) {
            this.errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }
}

