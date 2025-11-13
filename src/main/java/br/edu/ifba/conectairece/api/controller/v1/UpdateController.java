package br.edu.ifba.conectairece.api.controller.v1;

import br.edu.ifba.conectairece.api.features.update.domain.dto.request.UpdateRequestDTO;
import br.edu.ifba.conectairece.api.features.update.domain.dto.response.UpdateResponseDTO;
import br.edu.ifba.conectairece.api.features.update.domain.service.IUpdateService;
import br.edu.ifba.conectairece.api.infraestructure.util.ResultError;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Giovane Neves
 */
@RestController
@RequestMapping(path = "/api/v1/updates")
@RequiredArgsConstructor
public class UpdateController {

    private final IUpdateService updateService;


    @Operation(summary = "List all updates with pagination",
            description = "Retrieves a paginated list of updates")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paginated list of updates",
                    content = @Content(schema = @Schema(implementation = UpdateResponseDTO.class)))
    })
    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAll(@PageableDefault(size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(this.updateService.getUpdateList(pageable));

    }

    @Operation(summary = "Create a new Update",
       description = "Creates and persists a new update in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update successfully created",
                    content = @Content(schema = @Schema(implementation = UpdateRequestDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "422", description = "One or some fields are invalid")
    })
    @PostMapping("/update")
    public ResponseEntity<?> save(@RequestBody UpdateRequestDTO dto, BindingResult result){

        return result.hasErrors()
                ? ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResultError.getResultErrors(result))
                : ResponseEntity.ok(this.updateService.save(dto));

    }

}
