package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.controller;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.dto.request.FlowRequestDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.dto.request.FlowStepRequestDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.dto.response.FlowFullDataResponseDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.service.IFlowService;
import br.com.cidadesinteligentes.infraestructure.util.ObjectMapperUtil;
import br.com.cidadesinteligentes.infraestructure.util.ResultError;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author Giovane Neves
 */
@RestController
@RequestMapping("/api/v1/flows")
@RequiredArgsConstructor
public class FlowController {

    private final ObjectMapperUtil objectMapperUtil;
    private final IFlowService flowService;

    @Operation(summary = "Create a new Municipal Service Flow",
            description = "Creates and persists a new Municipal Service Flow in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Flow successfully created",
                    content = @Content(schema = @Schema(implementation = FlowRequestDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request body",
                    content = @Content),
            @ApiResponse(responseCode = "422", description = "One or some fields are invalid", content = @Content)
    })
    @PostMapping(value = "/flow", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> createFlow(
            @RequestBody @Valid FlowRequestDTO dto,
            BindingResult result
    ) {

        return result.hasErrors()
                ? ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ResultError.getResultErrors(result))
                : ResponseEntity.status(HttpStatus.CREATED).body(this.flowService.createFlow(dto));

    }

    @Operation(summary = "Create a new Municipal Service Flow Step",
            description = "Creates and persists a new Municipal Service Flow Step in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "FlowStep successfully created",
                    content = @Content(schema = @Schema(implementation = FlowRequestDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request body",
                    content = @Content),
            @ApiResponse(responseCode = "422", description = "One or some fields are invalid", content = @Content)
    })
    @PostMapping(value = "/flow/steps/step", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> createFlowStep(
            @RequestBody @Valid FlowStepRequestDTO dto,
            BindingResult result
    ){

        return result.hasErrors()
                ? ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ResultError.getResultErrors(result))
                : ResponseEntity.status(HttpStatus.CREATED).body(this.flowService.createFlowStep(dto));
    }

    @Operation(summary = "List all flows",
            description = "Retrieves a list of all registered flows.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of flows retrieved",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = FlowFullDataResponseDTO.class))))
    })
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> getAll(){

        return ResponseEntity.status(HttpStatus.OK).body(this.flowService.getAllFlows());

    }

    @Operation(summary = "Get a flow by the id passed as a parameter")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Get a flow by the id passed as a parameter",
                    content = @Content(schema = @Schema(implementation = FlowFullDataResponseDTO.class))
            )
    })
    @GetMapping(value = "/flow/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> getFlowById(@PathVariable("id") UUID id){

        return ResponseEntity.status(HttpStatus.OK)
                .body(this.flowService.getFlowById(id));

    }


}
