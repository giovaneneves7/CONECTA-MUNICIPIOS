package br.edu.ifba.conectairece.api.controller.v1;

import br.edu.ifba.conectairece.api.features.auth.domain.model.Role;
import br.edu.ifba.conectairece.api.features.permission.domain.dto.request.PermissionRequestAddDTO;
import br.edu.ifba.conectairece.api.features.role.domain.service.IRoleService;
import br.edu.ifba.conectairece.api.infraestructure.util.ResultError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller responsible for managing {@link Role} resources.
 *
 * @author Jorge Roberto
 */
@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {
    private final IRoleService roleService;

    @Operation(summary = "Adds a permission to a role",
            description = "Adds one of the existing permissions in the database to a role.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Permission added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request content", content = @Content),
            @ApiResponse(responseCode = "404", description = "Permission or Role not found"),
            @ApiResponse(responseCode = "409", description = "This role already has this permission"),
            @ApiResponse(responseCode = "422", description = "One or some fields are invalid", content = @Content)
    })
    @PatchMapping("/role/{roleId}/permissions")
    public ResponseEntity<?> addPermission(
        @PathVariable("roleId") @NotNull Long roleId,
        @RequestBody @Valid PermissionRequestAddDTO dto,
        BindingResult result
    ) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ResultError.getResultErrors(result));
        }
        roleService.addPermission(dto.permissionName(), roleId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Remove a permission to a role",
            description = "Remove one of the permissions from a role.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Permission removed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request content", content = @Content),
            @ApiResponse(responseCode = "404", description = "Permission or Role not found"),
            @ApiResponse(responseCode = "422", description = "One or some fields are invalid", content = @Content)
    })
    @DeleteMapping("/role/{roleId}/permissions/remove")
    public ResponseEntity<?> removePermission(
        @PathVariable("roleId") @NotNull Long roleId,
        @RequestBody @Valid PermissionRequestAddDTO dto,
        BindingResult result
    ) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ResultError.getResultErrors(result));
        }
        roleService.removePermission(dto.permissionName(), roleId);
        return ResponseEntity.noContent().build();
    }
}
