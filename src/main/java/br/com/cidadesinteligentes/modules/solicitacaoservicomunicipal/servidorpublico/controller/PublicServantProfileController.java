package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servidorpublico.controller;

import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.documento.service.IDocumentService;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servidorpublico.dto.request.PublicServantApproveDocumentRequestDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servidorpublico.dto.request.PublicServantCreationRequestDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servidorpublico.dto.request.PublicServantRejectDocumentRequestDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servidorpublico.dto.response.PublicServantRegisterResponseDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servidorpublico.model.PublicServantProfile;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servidorpublico.service.IPublicServantProfileService;
import br.com.cidadesinteligentes.infraestructure.util.ObjectMapperUtil;
import br.com.cidadesinteligentes.infraestructure.util.ResultError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller responsible for managing {@link PublicServantProfile}
 * resources.
 *
 * @author Jorge Roberto, Andesson Reis
 */
@RestController
@RequestMapping("/api/v1/public-servant-profiles")
@RequiredArgsConstructor
public class PublicServantProfileController {

        private final IPublicServantProfileService publicServantService;
        private final ObjectMapperUtil objectMapperUtil;
        private final IDocumentService documentService;

        @Operation(summary = "Create a new Public Servant Profile, if your user has a admin profile", description = "Creates and persists a new Public Servant Profile in the system.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Profile successfully created", content = @Content(schema = @Schema(implementation = PublicServantCreationRequestDTO.class))),
                        @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content),
                        @ApiResponse(responseCode = "409", description = "This user already has a public servant profile", content = @Content),
                        @ApiResponse(responseCode = "422", description = "One or some fields are invalid", content = @Content)
        })
        @PostMapping(value = "/public-servant-profile", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<?> createPublicServantProfile(
                        @RequestBody @Valid PublicServantCreationRequestDTO dto,
                        BindingResult result) {
                if (result.hasErrors()) {
                        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                                        .body(ResultError.getResultErrors(result));
                }
                PublicServantProfile publicServantProfile = objectMapperUtil.map(dto, PublicServantProfile.class);
                PublicServantRegisterResponseDTO response = this.publicServantService
                                .createPublicServantProfile(dto.userId(), publicServantProfile);
                return ResponseEntity.ok(response);
        }

        /**
         * Endpoint for the Public Servant to perform the final approval on a document.
         *
         * This endpoint allows a Public Servant to approve a document, changing its
         * status. Only documents that have already been approved or corrected in the
         * previous review step can be approved by the Public Servant.
         * Validation errors are returned with status 422.
         *
         * @param dto    DTO containing documentId, publicServantProfileId, and optional
         *               comment.
         * @param result BindingResult containing validation results for the request
         *               body.
         * @return ResponseEntity containing the updated document status or validation
         *         errors.
         * 
         * 
         */
        @Operation(summary = "Approve a Document by Public Servant", description = "Allows a Public Servant to perform the final approval on a document. "
                        + "The document must have status APPROVED or CORRECTION_SUGGESTED from the previous review step.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Document successfully approved."),
                        @ApiResponse(responseCode = "400", description = "Invalid request or document not in a state eligible for SP approval."),
                        @ApiResponse(responseCode = "404", description = "Document or Public Servant Profile not found."),
                        @ApiResponse(responseCode = "422", description = "Validation error in request body.")
        })
        @PostMapping(value = "/documents/approve-review", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<?> approveDocumentByPublicServant(
                        @RequestBody @Valid PublicServantApproveDocumentRequestDTO dto,
                        BindingResult result) {
                if (result.hasErrors()) {
                        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                                        .body(ResultError.getResultErrors(result));
                }
                return ResponseEntity.ok(documentService.approveDocumentByPublicServant(dto));
        }

        /**
         * Endpoint for the Public Servant to perform the final rejection on a document.
         *
         * This endpoint allows a Public Servant to reject a document, requiring a
         * justification. Only documents that have already been approved or corrected in
         * the previous review step can be rejected by the Public Servant.
         * Validation errors are returned with status 422.
         *
         * @param dto    DTO containing documentId, publicServantProfileId, and
         *               mandatory justification.
         * @param result BindingResult containing validation results for the request
         *               body.
         * @return ResponseEntity containing the updated document status or validation
         *         errors.
         * 
         *  
         */
        @Operation(summary = "Reject a Document by Public Servant", description = "Allows a Public Servant to perform the final rejection on a document, "
                        + "requiring a justification. The document must have status APPROVED or CORRECTION_SUGGESTED from the previous review step.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Document successfully rejected."),
                        @ApiResponse(responseCode = "400", description = "Invalid request or document not in a state eligible for SP rejection."),
                        @ApiResponse(responseCode = "404", description = "Document or Public Servant Profile not found."),
                        @ApiResponse(responseCode = "422", description = "Validation error in request body.")
        })
        @PostMapping(value = "/documents/reject-review", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<?> rejectDocumentByPublicServant(
                        @RequestBody @Valid PublicServantRejectDocumentRequestDTO dto,
                        BindingResult result) {
                if (result.hasErrors()) {
                        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                                        .body(ResultError.getResultErrors(result));
                }
                return ResponseEntity.ok(documentService.rejectDocumentByPublicServant(dto));
        }
}
