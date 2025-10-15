package br.edu.ifba.conectairece.api.controller.v1;

import br.edu.ifba.conectairece.api.features.step.domain.dto.StepRequestDTO;
import br.edu.ifba.conectairece.api.features.step.domain.model.Step;
import br.edu.ifba.conectairece.api.features.step.domain.service.IStepService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Giovane Neves
 */
@RestController
@RequestMapping("/api/v1/steps")
@RequiredArgsConstructor
public class StepController {

    private final IStepService stepService;
    private final ObjectMapperUtil objectMapperUtil;

    @Operation(summary = "Create a new Flow step",
            description = "Creates and persists a new Municipal Service Flow's step in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Step successfully created",
                    content = @Content(schema = @Schema(implementation = StepRequestDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request body",
                    content = @Content),
            @ApiResponse(responseCode = "422", description = "One or some fields are invalid", content = @Content)
    })
    @PostMapping(value = "/step", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> createAdmin(
            @RequestBody @Valid StepRequestDTO dto,
            BindingResult result
    ) {

        return result.hasErrors()
                ? ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ResultError.getResultErrors(result))
                : ResponseEntity.status(HttpStatus.CREATED).body(this.stepService.createStep(this.objectMapperUtil.map(dto, Step.class)));

    }
}
