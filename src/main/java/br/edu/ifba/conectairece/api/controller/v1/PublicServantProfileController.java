package br.edu.ifba.conectairece.api.controller.v1;

import br.edu.ifba.conectairece.api.features.publicservantprofile.domain.dto.request.PublicServantCreationRequest;
import br.edu.ifba.conectairece.api.features.publicservantprofile.domain.model.PublicServantProfile;
import br.edu.ifba.conectairece.api.features.publicservantprofile.domain.service.IPublicServantProfileService;
import br.edu.ifba.conectairece.api.infraestructure.util.ObjectMapperUtil;
import br.edu.ifba.conectairece.api.infraestructure.util.ResultError;
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
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller responsible for managing {@link PublicServantProfile} resources.
 *
 * @author Jorge Roberto
 */
@RestController
@RequestMapping("/api/v1/public-servant-profiles")
@RequiredArgsConstructor
public class PublicServantProfileController {

    private final IPublicServantProfileService publicServantService;
    private final ObjectMapperUtil objectMapperUtil;

    @Operation(summary = "Create a new Public Servant Profile, if your user has a admin profile",
            description = "Creates and persists a new Public Servant Profile in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Profile successfully created",
                    content = @Content(schema = @Schema(implementation = PublicServantCreationRequest.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request body",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "This user already has a public servant profile",
                    content = @Content),
            @ApiResponse(responseCode = "422", description = "One or some fields are invalid", content = @Content)
    })
    @PostMapping(value = "/public-servant-profile", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createPublicServantProfile (
            @RequestBody @Valid PublicServantCreationRequest dto,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ResultError.getResultErrors(result));
        }
        PublicServantProfile publicServantProfile = objectMapperUtil.map(dto, PublicServantProfile.class);
        this.publicServantService.createPublicServantProfile(dto.userId(), publicServantProfile);
        return ResponseEntity.noContent().build();
    }
}
