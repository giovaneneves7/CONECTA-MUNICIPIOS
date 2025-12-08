package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.acompanhamento.controller;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.acompanhamento.dto.request.AcompanhamentoAtualizacaoRequestDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.acompanhamento.dto.request.AcompanhamentoRequestDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.acompanhamento.dto.response.AcompanhamentoResponseDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.acompanhamento.service.IAcompanhamentoService;
import br.com.cidadesinteligentes.infraestructure.util.ResultError;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller responsável por manipular os endpoints referentes a Acompanhamentos.
 * Fornece operações para criar, atualizar e listar acompanhamentos.
 *
 * @author Giovane Neves, Andesson Reis
 */
@RestController
@RequestMapping(path = "/api/v1/acompanhamentos")
@RequiredArgsConstructor
public class AcompanhamentoController {

    private final IAcompanhamentoService acompanhamentoService;

    /**
     * Endpoint para criar um novo acompanhamento.
     *
     * @param dto DTO contendo os dados do acompanhamento.
     * @param result Resultado da validação dos dados.
     * @return Resposta com os dados do acompanhamento criado.
     */
    @Operation(summary = "Criar um novo Acompanhamento",
            description = "Cria e persiste um novo acompanhamento no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Acompanhamento criado com sucesso",
                    content = @Content(schema = @Schema(implementation = AcompanhamentoRequestDTO.class))),
            @ApiResponse(responseCode = "400", description = "Corpo da requisição inválido"),
            @ApiResponse(responseCode = "422", description = "Um ou mais campos são inválidos")
    })
    @PostMapping(path = "/acompanhamento", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> salvar(@RequestBody @Valid AcompanhamentoRequestDTO dto, BindingResult result){

        return result.hasErrors()
                ? ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResultError.getResultErrors(result))
                : ResponseEntity.status(HttpStatus.CREATED).body(this.acompanhamentoService.save(dto));

    }

    /**
     * Endpoint para atualizar um acompanhamento existente.
     *
     * @param dto DTO contendo os dados do acompanhamento a ser atualizado.
     * @param result Resultado da validação dos dados.
     * @return Resposta com os dados do acompanhamento atualizado.
     */
    @Operation(summary = "Atualizar um Acompanhamento existente",
            description = "Atualiza um acompanhamento existente no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Acompanhamento atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = AcompanhamentoAtualizacaoRequestDTO.class))),
            @ApiResponse(responseCode = "400", description = "Corpo da requisição inválido"),
            @ApiResponse(responseCode = "422", description = "Um ou mais campos são inválidos")
    })
    @PutMapping(path = "/acompanhamento", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> atualizar(@RequestBody @Valid AcompanhamentoAtualizacaoRequestDTO dto, BindingResult result){

        return result.hasErrors()
                ? ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResultError.getResultErrors(result))
                : ResponseEntity.status(HttpStatus.OK).body(this.acompanhamentoService.update(dto));

    }

    /**
     * Endpoint para listar acompanhamentos com paginação.
     *
     * @param pageable Objeto de paginação e ordenação.
     * @return Lista paginada de acompanhamentos.
     */
    @Operation(summary = "Listar todos os acompanhamentos com paginação",
            description = "Recupera uma lista paginada de acompanhamentos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista paginada de acompanhamentos",
                    content = @Content(schema = @Schema(implementation = AcompanhamentoResponseDTO.class)))
    })
    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> listarTodos(@PageableDefault(size = 10, sort = "dataCriacao", direction = Sort.Direction.ASC) Pageable pageable) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(this.acompanhamentoService.findAll(pageable));

    }

}