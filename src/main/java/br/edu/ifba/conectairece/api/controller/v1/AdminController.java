package br.edu.ifba.conectairece.api.controller.v1;

import br.edu.ifba.conectairece.api.features.admin.domain.dto.request.AdminProfileRequestDTO;
import br.edu.ifba.conectairece.api.features.admin.domain.dto.response.AdminResponseDTO;
import br.edu.ifba.conectairece.api.features.admin.domain.model.AdminProfile;
import br.edu.ifba.conectairece.api.features.admin.domain.service.IAdminService;
import br.edu.ifba.conectairece.api.features.publicservantprofile.domain.dto.request.PublicServantCreationRequest;
import br.edu.ifba.conectairece.api.infraestructure.util.ObjectMapperUtil;
import br.edu.ifba.conectairece.api.infraestructure.util.ResultError;
import br.edu.ifba.conectairece.api.infraestructure.util.dto.PageableDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin-profiles")
@RequiredArgsConstructor
public class AdminController {
    private final ObjectMapperUtil objectMapperUtil;
    private final IAdminService adminService;

    @Operation(summary = "Create a new Admin Profile, if your user has a super admin profile",
            description = "Creates and persists a new Admin Profile in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile successfully created",
                    content = @Content(schema = @Schema(implementation = PublicServantCreationRequest.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request body",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "This user already has a admin profile",
                    content = @Content),
            @ApiResponse(responseCode = "422", description = "One or some fields are invalid", content = @Content)
    })
    @PostMapping(value = "/admin-profile", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> createAdmin(
            @RequestBody @Valid AdminProfileRequestDTO dto,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ResultError.getResultErrors(result));
        }
        AdminProfile adminProfile = objectMapperUtil.map(dto, AdminProfile.class);
        AdminResponseDTO response = this.adminService.createAdmin(dto.userId(), adminProfile);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update an existing Admin Profile",
            description = "Updates a profile by replacing its data with the provided payload. " +
                    "You need to pass the admin profile ID in the request instead of the user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Profile successfully updated"),
            @ApiResponse(responseCode = "400", description = "Invalid request content", content = @Content),
            @ApiResponse(responseCode = "404", description = "Profile not found"),
            @ApiResponse(responseCode = "422", description = "One or some fields are invalid", content = @Content)
    })
    @PutMapping(path = "/admin-profile", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> update (
            @RequestBody @Valid AdminProfileRequestDTO dto,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ResultError.getResultErrors(result));
        }

        this.adminService.update(dto.userId(), objectMapperUtil.map(dto, AdminProfile.class));
        return ResponseEntity.noContent().build();
    }

    /***
     * In this case you need to pass the admin profile ID
     */
    @Operation(summary = "Delete an admin profile",
            description = "Deletes an administrator profile by its ADMIN ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Profile successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Profile not found"),
    })
    @DeleteMapping(value = "/admin-profile/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> delete (
            @PathVariable("id") UUID id
    ) {
        this.adminService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "List all Admin Profiles with pagination",
            description = "Retrieves a paginated list of profiles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paginated list of profiles",
                    content = @Content(schema = @Schema(implementation = PageableDTO.class)))
    })
    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> findAll (
            @PageableDefault(
                    size = 10,
                    sort = "id",
                    direction = Sort.Direction.ASC
            ) Pageable pageable
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.adminService.findAll(pageable));
    }
}
