package br.edu.ifba.conectairece.api.infraestructure.exception;

import br.edu.ifba.conectairece.api.infraestructure.exception.custom.CpfUniqueViolationException;
import br.edu.ifba.conectairece.api.infraestructure.exception.custom.EmailUniqueViolationException;
import br.edu.ifba.conectairece.api.infraestructure.exception.custom.EntityNotFoundException;
import br.edu.ifba.conectairece.api.infraestructure.exception.custom.PasswordInvalidException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Global API exception handler that intercepts and manages
 * application-specific and general exceptions, returning
 * standardized error responses.
 *
 * <p>Each handler method maps a specific exception type to
 * a corresponding HTTP status code and builds a {@link ErrorMessage}
 * to be returned as JSON in the response body.</p>
 *
 * @author Jorge Roberto
 */
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler {

    /** Used for resolving localized error messages. */
    private final MessageSource messageSource;

    /**
     * Handles {@link EntityNotFoundException}.
     *
     * @param ex the thrown exception
     * @param request the current HTTP request
     * @return ResponseEntity with HTTP 404 and error details
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> entityNotFoundException(EntityNotFoundException ex, HttpServletRequest request) {
        Object[] params = new Object[]{ex.getRecourse(), ex.getCode()};
        String message = messageSource.getMessage("exception.entityNotFoundException", params, request.getLocale());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.NOT_FOUND, message));
    }

    /**
     * Handles {@link AccessDeniedException}.
     *
     * @param ex the thrown exception
     * @param request the current HTTP request
     * @return ResponseEntity with HTTP 403 and error details
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorMessage> accessDeniedException(AccessDeniedException ex, HttpServletRequest request) {
        log.error("Api Error : ", ex);
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.FORBIDDEN, ex.getMessage()));
    }

    /**
     * Handles {@link PasswordInvalidException}.
     *
     * @param ex the thrown exception
     * @param request the current HTTP request
     * @return ResponseEntity with HTTP 401 and error details
     */
    @ExceptionHandler(PasswordInvalidException.class)
    public ResponseEntity<ErrorMessage> passwordInvalidException(RuntimeException ex, HttpServletRequest request) {
        log.error("Api Error - ", ex);
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.UNAUTHORIZED, ex.getMessage()));
    }

    /**
     * Handles {@link CpfUniqueViolationException}.
     *
     * @param ex the thrown exception
     * @param request the current HTTP request
     * @return ResponseEntity with HTTP 409 and error details
     */
    @ExceptionHandler({CpfUniqueViolationException.class, EmailUniqueViolationException.class})
    public ResponseEntity<ErrorMessage> uniqueViolationException(RuntimeException ex, HttpServletRequest request) {
        log.error("Api Error : ", ex);
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.CONFLICT, ex.getMessage()));
    }

    /**
     * Handles {@link MethodArgumentNotValidException}.
     *
     * @param ex the thrown exception
     * @param request the current HTTP request
     * @param result binding result containing validation errors
     * @return ResponseEntity with HTTP 422 and validation error details
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> methodArgumentNotValidException(MethodArgumentNotValidException ex,
                                                                        HttpServletRequest request,
                                                                        BindingResult result) {
        log.error("Api Error : ", ex);

        result = ex.getBindingResult();

        // Get the errors in the annotations
        List<String> errors = result.getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        String combinedMessage = String.join("; ", errors);

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.UNPROCESSABLE_ENTITY, combinedMessage));
    }

    /**
     * Handles uncaught exceptions ({@link Exception}).
     *
     * @param ex the thrown exception
     * @param request the current HTTP request
     * @return ResponseEntity with HTTP 500 and error details
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> internalServerErrorException(Exception ex,
                                                                     HttpServletRequest request) {
        ErrorMessage error = new ErrorMessage(request, HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        log.error("Internal Server Error {} {} ", error, ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(error);
    }
}
