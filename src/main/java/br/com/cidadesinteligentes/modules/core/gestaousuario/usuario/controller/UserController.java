package br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.controller;

import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.dto.response.UserResponseDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.service.IUserService;
import br.com.cidadesinteligentes.infraestructure.util.ObjectMapperUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Controller responsible for user endpoints.
 *
 * @author Jorge Roberto
 */
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;
    private final ObjectMapperUtil objectMapperUtil;

    /**
     * Endpoint to get a user by the id passed as a parameter
     *
     * @param id The id of the user to be found
     * @return Response with user data
     */
    @GetMapping(path = "/user/{id}", produces = "application/json")
    public ResponseEntity<?> getUserById(@PathVariable("id") @NotNull UUID id){

        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.getUserById(id));

    }

    /**
     * Endpoint to get the user's profile by the id passed as a parameter
     *
     * @author Giovane Neves
     * @param id The id of the user
     * @return Response with the user's profiles data
     */
    @GetMapping(path = "/user/{id}/profiles", produces = "application/json")
    public ResponseEntity<?> getUserProfiles(@PathVariable("id") @NotNull UUID id, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){

        return ResponseEntity.status((HttpStatus.OK))
                .body(userService.getUserProfiles(id, pageable));

    }

    @GetMapping(path = "/user/{id}/profiles/profile/active", produces = "application/json")
    public ResponseEntity<?> getMyActiveProfile(@PathVariable("id") UUID userId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.userService.findActiveProfileByUserId(userId));
    }

    @Operation(summary = "List all Users",
            description = "Retrieves a list of all registered users.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of users retrieved",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserResponseDTO.class))))
    })
    @GetMapping
    public ResponseEntity<?> findAllUsers(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(this.userService.findAllUsers(pageable));
    }
}
