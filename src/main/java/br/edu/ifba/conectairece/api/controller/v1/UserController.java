package br.edu.ifba.conectairece.api.controller.v1;

import br.edu.ifba.conectairece.api.features.user.domain.service.IUserService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<?> getUserProfiles(@PathVariable("id") @NotNull UUID id){

        return ResponseEntity.status((HttpStatus.OK))
                .body(userService.getUserProfiles(id));

    }
}
