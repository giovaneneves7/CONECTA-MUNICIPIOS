package br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.documento.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Data Transfer Object (DTO) used as the request body for rejecting a document.
 * <p>
 * This class captures the mandatory justification required when an administrator
 * rejects a submitted document.
 * </p>
 *
 * @author Andesson Reis
 */
@Data
public class DocumentRejectionDTO {

    /**
     * The detailed reason why the document is being rejected.
     * This field is mandatory and must meet the specified size constraints.
     */
    @NotBlank(message = "Rejection justification cannot be blank.")
    @Size(min = 10, max = 500, message = "Justification must be between 10 and 500 characters.")
    private String justification;
}