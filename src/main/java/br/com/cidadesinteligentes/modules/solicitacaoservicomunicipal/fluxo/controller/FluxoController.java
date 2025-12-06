package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.controller;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.dto.request.AtividadeFluxoRequestDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.dto.request.FluxoRequestDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.dto.response.FluxoDadosCompletosResponseDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.service.IFluxoService;

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
 * Controller responsável por manipular os endpoints referentes a Fluxos e suas
 * atividades.
 *
 * <p>
 * Gerencia a criação de fluxos e a vinculação de atividades (Atividade) a estes
 * fluxos.
 * </p>
 *
 * @author Giovane Neves, Andesson Reis
 */
@RestController
@RequestMapping("/api/v1/fluxos")
@RequiredArgsConstructor
public class FluxoController {

        private final IFluxoService fluxoService;

        /**
         * Endpoint para criar um novo Fluxo de Serviço Municipal.
         *
         * @param dto    DTO contendo os dados do fluxo.
         * @param result Resultado da validação.
         * @return O fluxo criado ou erro de validação.
         */
        @Operation(summary = "Criar um novo Fluxo", description = "Cria e persiste um novo Fluxo de Serviço Municipal no sistema.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Fluxo criado com sucesso", content = @Content(schema = @Schema(implementation = FluxoRequestDTO.class))),
                        @ApiResponse(responseCode = "400", description = "Corpo da requisição inválido", content = @Content),
                        @ApiResponse(responseCode = "422", description = "Um ou mais campos são inválidos", content = @Content)
        })
        @PostMapping(value = "/fluxo", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
        ResponseEntity<?> criarFluxo(
                        @RequestBody @Valid FluxoRequestDTO dto,
                        BindingResult result) {

                return result.hasErrors()
                                ? ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                                                .body(ResultError.getResultErrors(result))
                                : ResponseEntity.status(HttpStatus.CREATED).body(this.fluxoService.criarFluxo(dto));

        }

        /**
         * Endpoint para adicionar uma nova atividade ao Fluxo.
         *
         * <p>
         * Vincula uma {@code Atividade} existente a um {@code Fluxo}, definindo sua
         * ordem de execução.
         * </p>
         *
         * @param dto    DTO contendo o ID do fluxo, ID da atividade e a ordem.
         * @param result Resultado da validação.
         * @return A atividade criada ou erro de validação.
         */
        @Operation(summary = "Adicionar Atividade ao Fluxo", description = "Cria e persiste uma nova AtividadeFluxo, vinculando uma Atividade ao Fluxo de Serviço Municipal.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Atividade criada com sucesso", content = @Content(schema = @Schema(implementation = AtividadeFluxoRequestDTO.class))),
                        @ApiResponse(responseCode = "400", description = "Corpo da requisição inválido", content = @Content),
                        @ApiResponse(responseCode = "422", description = "Um ou mais campos são inválidos", content = @Content)
        })
        @PostMapping(value = "/fluxo/atividade", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
        ResponseEntity<?> criarAtividadeFluxo(
                        @RequestBody @Valid AtividadeFluxoRequestDTO dto,
                        BindingResult result) {

                return result.hasErrors()
                                ? ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                                                .body(ResultError.getResultErrors(result))
                                : ResponseEntity.status(HttpStatus.CREATED)
                                                .body(this.fluxoService.criarAtividadeFluxo(dto));
        }

        /**
         * Endpoint para listar todos os fluxos.
         *
         * @return Lista de fluxos cadastrados com seus dados completos.
         */
        @Operation(summary = "Listar todos os fluxos", description = "Recupera uma lista de todos os fluxos registrados.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Lista de fluxos recuperada", content = @Content(array = @ArraySchema(schema = @Schema(implementation = FluxoDadosCompletosResponseDTO.class))))
        })
        @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
        ResponseEntity<?> listarTodos() {

                return ResponseEntity.status(HttpStatus.OK).body(this.fluxoService.buscarTodosFluxos());

        }

        /**
         * Endpoint para buscar um fluxo pelo ID.
         *
         * @param id UUID do fluxo.
         * @return O fluxo encontrado com seus detalhes.
         */
        @Operation(summary = "Buscar um fluxo pelo ID", description = "Recupera os detalhes de um fluxo através do ID passado como parâmetro.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Fluxo encontrado", content = @Content(schema = @Schema(implementation = FluxoDadosCompletosResponseDTO.class))),
                        @ApiResponse(responseCode = "404", description = "Fluxo não encontrado")
        })
        @GetMapping(value = "/fluxo/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
        ResponseEntity<?> buscarFluxoPorId(@PathVariable("id") UUID id) {

                return ResponseEntity.status(HttpStatus.OK)
                                .body(this.fluxoService.buscarFluxoPorId(id));

        }

}