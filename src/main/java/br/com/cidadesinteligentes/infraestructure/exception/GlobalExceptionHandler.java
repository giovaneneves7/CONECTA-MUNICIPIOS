package br.com.cidadesinteligentes.infraestructure.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Global exception handling, returns JSON error responses to the HTTP request when an exception is thrown.
 *
 * @author Jorge Roberto
 */
@Slf4j(topic = "GLOBAL_EXCEPTION_HANDLER")
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // =========================================================== //
    // =============== [        ATTRIBUTES       ] ================ //
    // =========================================================== //

    /**
     * Value that determines whether the 'stackTrace' should be shown to the client.
     */
    @Value(value = "${server.error.include-exception:false}")
    private boolean printStackTrace;


    // =========================================================== //
    // =============== [        METHODS       ] ================== //
    // =========================================================== //

    /**
     * Handles generic validation exceptions.
     *
     * @param methodArgumentNotValidException - Invalid argument.
     * @param headers - HTTP request headers.
     * @param status - The HTTP request status code.
     * @param request - The HTTP request.
     * @return a generic entity with the error message.
     */
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            final MethodArgumentNotValidException methodArgumentNotValidException,
            final HttpHeaders headers,
            final HttpStatus status,
            final WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Validation error. See the 'errors' field for more details.");

        for (FieldError fieldError : methodArgumentNotValidException.getBindingResult().getFieldErrors()) {
            errorResponse.addValidationError(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity.unprocessableEntity().body(errorResponse);
    }

    /**
     * Handles the generic 'Exception'.
     *
     * @param exception - The 'Exception' instance.
     * @param request - The HTTP request.
     * @return a generic entity with the error message.
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleAllUncaughtException(
            final Exception exception,
            final WebRequest request) {

        final String errorMessage = "An unexpected error occurred";

        log.error(errorMessage, exception);

        return buildErrorMessage(
                exception,
                errorMessage,
                HttpStatus.INTERNAL_SERVER_ERROR,
                request);
    }

    /**
     * Handles BusinessException dynamically for different cases.
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusinessException(BusinessException ex, WebRequest request) {
        final String errorMessage = ex.getMessage();
        HttpStatus status = HttpStatus.BAD_REQUEST;

        if (BusinessExceptionMessage.CLASS_IN_USE.getMessage().equals(errorMessage)) {
            status = HttpStatus.CONFLICT;
        } else if (BusinessExceptionMessage.INVALID_CREDENTIALS.getMessage().equals(errorMessage)) {
            status = HttpStatus.UNAUTHORIZED;
        } else if (BusinessExceptionMessage.ATTRIBUTE_VALUE_ALREADY_EXISTS.getMessage().equals(errorMessage)
                || BusinessExceptionMessage.USER_ALREADY_HAS_THIS_PROFILE.getMessage().equals(errorMessage)
                || BusinessExceptionMessage.PROFILE_ALREADY_HAS_THIS_PERMISSION.getMessage().equals(errorMessage)
                || BusinessExceptionMessage.INVALID_REQUEST_TO_FINALIZE.getMessage().equals(errorMessage)
        )
        {
            status = HttpStatus.CONFLICT;
        } else if (BusinessExceptionMessage.INVALID_PROFILE.getMessage().equals(errorMessage) || BusinessExceptionMessage.INVALID_DATA.getMessage().equals(errorMessage)) {
            status = HttpStatus.BAD_REQUEST;
        } else if (BusinessExceptionMessage.USER_WITHOUT_PROFILES.getMessage().equals(errorMessage)) {
            status = HttpStatus.FORBIDDEN;
        } else if (BusinessExceptionMessage.FINAL_APPROVAL_CANNOT_OCCUR.getMessage().equals(errorMessage)) {
            status = HttpStatus.UNPROCESSABLE_ENTITY;
        }

        log.error(errorMessage, ex);
        return buildErrorMessage(ex, errorMessage, status, request);
    }

    /**
     * Handles the 'UsernameNotFoundException'.
     *
     * @param exception - The 'UsernameNotFoundException' instance.
     * @param request - The HTTP request.
     * @return a generic entity with the error message.
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleUsernameNotFoundException(
            final UsernameNotFoundException exception,
            final WebRequest request) {

        final String errorMessage = exception.getMessage();

        log.error(errorMessage, exception);

        return buildErrorMessage(
                exception,
                errorMessage,
                HttpStatus.INTERNAL_SERVER_ERROR,
                request);
    }
    /**
     * Builds an error message.
     *
     * @param exception  The exception.
     * @param message    The exception message.
     * @param httpStatus The HTTP status.
     * @param request    The request.
     * @return the error message response.
     */
    private ResponseEntity<Object> buildErrorMessage(
            final Exception exception,
            final String message,
            final HttpStatus httpStatus,
            final WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(), message);
        if (this.printStackTrace) {
            errorResponse.setStacktrace(ExceptionUtils.getStackTrace(exception));
        }

        return ResponseEntity.status(httpStatus).body(errorResponse);
    }
}