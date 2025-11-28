package br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.controller;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servicomunicipal.dto.response.MunicipalServiceResponseDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.permissao.dto.response.PermissionResponseDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.dto.request.ProfileRequestUpdateChangeProfileTypeDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.dto.request.ProfileUpdateRequestDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.dto.response.ProfileResponseCurrentTypeDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.dto.response.ProfileSimpleResponseDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.model.Profile;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.service.IProfileService;
import br.com.cidadesinteligentes.infraestructure.util.ObjectMapperUtil;
import br.com.cidadesinteligentes.infraestructure.util.ResultError;
import br.com.cidadesinteligentes.infraestructure.util.dto.PageableDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * REST Controller responsible for managing {@link Profile} resources.
 *
 * @author Jorge Roberto
 */
@RestController
@RequestMapping("/api/v1/profiles")
@RequiredArgsConstructor
public class ProfileController {
    private final IProfileService profileService;
    private final ObjectMapperUtil objectMapperUtil;

    @Operation(summary = "Update an existing Profile",
            description = "Updates a profile by replacing its data with the provided payload.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Profile successfully updated"),
            @ApiResponse(responseCode = "400", description = "Invalid request content", content = @Content),
            @ApiResponse(responseCode = "404", description = "Profile not found"),
            @ApiResponse(responseCode = "422", description = "One or some fields are invalid", content = @Content)
    })
    @PutMapping(path = "/profile", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestBody @Valid ProfileUpdateRequestDTO body, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ResultError.getResultErrors(result));
        }

        this.profileService.update(objectMapperUtil.map(body, Profile.class));
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete a profile",
            description = "Deletes a profile by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Profile successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Profile not found"),
            @ApiResponse(responseCode = "409", description = "Profile is in use " +
                    "by another class"),
    })
    @DeleteMapping(value = "/profile/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProfileSimpleResponseDTO> delete(@PathVariable("id") UUID id) {
        profileService.delete(id);
        return ResponseEntity.ok(new ProfileSimpleResponseDTO(id));
    }

    @Operation(summary = "List all Profiles with pagination",
            description = "Retrieves a paginated list of profiles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paginated list of profiles",
                    content = @Content(schema = @Schema(implementation = PageableDTO.class)))
    })
    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAll(@ParameterObject @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(this.profileService.getAllProfiles(pageable));

    }


    /**
     * Endpoint to get the profile by the attribute passed as a parameter.
     *
     * @author Giovane Neves
     * @return
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found", content = @Content(schema = @Schema(implementation = MunicipalServiceResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping(path = "/profile/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserId(@Valid @PathVariable("id") final UUID profileId){

        return ResponseEntity.status(HttpStatus.OK).body(this.profileService.findById(profileId));

    }

    /**
     * Endpoint to get all requests by the profile ID.
     *
     * @author Giovane Neves
     * @return
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request list found", content = @Content(schema = @Schema(implementation = MunicipalServiceResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Request list not found")
    })
    @GetMapping(path = "/profile/{id}/requests", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<?>> getRequestListByUserId(@Valid @PathVariable("id") final UUID profileId, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){

        return ResponseEntity.status(HttpStatus.OK).body(this.profileService.findAllRequestsByProfileId(profileId, pageable));

    }

    @Operation(summary = "Update the active profile type for the current user",
            description = "Changes the active profile of the authenticated user to a new valid type.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile type successfully updated",
                    content = @Content(schema = @Schema(implementation = ProfileResponseCurrentTypeDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid profile type provided",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found or Profile not found",
                    content = @Content),
            @ApiResponse(responseCode = "422", description = "Validation error in the request body",
                    content = @Content)
    })
    @PatchMapping(path = "/profile", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateActiveTypeProfile(
            @RequestBody @Valid ProfileRequestUpdateChangeProfileTypeDTO dto,
            BindingResult result
    ) {
        return result.hasErrors()
                ? ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ResultError.getResultErrors(result))
                : ResponseEntity.status(HttpStatus.CREATED).body(this.profileService.changeActiveProfile(dto.userId(), dto.activeType()));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Find all permissions for a profile",
                    content = @Content(schema = @Schema(implementation = PermissionResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "List not found")
    })
    @GetMapping(path = "/profile/{profileId}/permissions", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAllPermissionsByProfile (
            @PathVariable("profileId") UUID profileId,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(this.profileService.findAllPermissionsByProfile(profileId, pageable));
    }
}
