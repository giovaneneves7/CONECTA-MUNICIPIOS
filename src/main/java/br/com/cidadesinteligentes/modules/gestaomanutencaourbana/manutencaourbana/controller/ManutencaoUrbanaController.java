package br.com.cidadesinteligentes.modules.gestaomanutencaourbana.manutencaourbana.controller;

import br.com.cidadesinteligentes.infraestructure.exception.BusinessException;
import br.com.cidadesinteligentes.infraestructure.exception.BusinessExceptionMessage;
import br.com.cidadesinteligentes.infraestructure.util.ResultError;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.manutencaourbana.dto.request.ManutencaoUrbanaAtualizarRequestDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.manutencaourbana.dto.request.ManutencaoUrbanaCriarRequestDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.manutencaourbana.dto.response.ManutencaoUrbanaResponseDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.manutencaourbana.dto.response.ManutencaoUrbanaRetornarIdResponseDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.manutencaourbana.service.IManutencaoUrbanaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gerenciamento de solicitações de manutenção urbana.
 *
 * @see IManutencaoUrbanaService
 */
@RestController
@RequestMapping("/api/v1/manutencao-urbana/solicitacoes")
@Tag(name = "Solicitações de Manutenção", description = "Endpoints para criar, listar, atualizar e remover solicitações de manutenção urbana")
@RequiredArgsConstructor
public class ManutencaoUrbanaController {

    private final IManutencaoUrbanaService service;

    /**
     * Registra uma nova manutenção urbana.
     *
     * @param dto    Dados da solicitação.
     * @param result Validação.
     * @return Solicitação criada.
     */
    @Operation(summary = "Criar Nova Solicitação", description = "Registra uma nova manutenção urbana no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Solicitação criada com sucesso."),
            @ApiResponse(responseCode = "422", description = "Erro de validação nos dados enviados.")
    })
    @PostMapping(value = "/solicitacao",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@RequestBody @Valid ManutencaoUrbanaCriarRequestDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(ResultError.getResultErrors(result));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
    }

    /**
     * Lista todas as solicitações.
     *
     * @return Lista de manutenções.
     */
    @Operation(summary = "Listar Solicitações", description = "Retorna todas as solicitações de manutenção cadastradas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista recuperada com sucesso.",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ManutencaoUrbanaResponseDTO.class))))
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ManutencaoUrbanaResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    /**
     * Busca solicitação por ID.
     *
     * @param id ID da solicitação.
     * @return Dados da solicitação.
     */
    @Operation(summary = "Buscar por ID", description = "Recupera os detalhes de uma solicitação específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Solicitação encontrada."),
            @ApiResponse(responseCode = "404", description = "Solicitação não encontrada.")
    })
    @GetMapping("/solicitacao/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    /**
     * Atualiza uma solicitação existente.
     *
     * @param id     ID da solicitação.
     * @param dto    Dados atualizados.
     * @param result Validação.
     * @return Solicitação atualizada.
     */
    @Operation(summary = "Atualizar Solicitação", description = "Atualiza os dados de uma manutenção urbana.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atualizado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Solicitação não encontrada."),
            @ApiResponse(responseCode = "422", description = "Erro de validação.")
    })
    @PutMapping("/solicitacao/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @RequestBody @Valid ManutencaoUrbanaAtualizarRequestDTO dto,
                                    BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(ResultError.getResultErrors(result));
        }

        if (!id.equals(dto.id())) {
            throw new BusinessException(BusinessExceptionMessage.ID_MISMATCH.getMessage());
        }

        return ResponseEntity.ok(service.update(dto));
    }

    /**
     * Remove uma solicitação.
     *
     * @param id ID da solicitação.
     * @return ID da solicitação removida.
     */
    @Operation(summary = "Deletar Solicitação", description = "Remove uma solicitação do sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Removido com sucesso."),
            @ApiResponse(responseCode = "404", description = "Solicitação não encontrada.")
    })
    @DeleteMapping("/solicitacao/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        ManutencaoUrbanaRetornarIdResponseDTO dtoDeletado = service.delete(id);
        return ResponseEntity.ok(dtoDeletado);
    }
}