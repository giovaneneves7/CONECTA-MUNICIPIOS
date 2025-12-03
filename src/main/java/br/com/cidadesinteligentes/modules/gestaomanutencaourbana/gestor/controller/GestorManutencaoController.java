package br.com.cidadesinteligentes.modules.gestaomanutencaourbana.gestor.controller;

import br.com.cidadesinteligentes.infraestructure.exception.BusinessException;
import br.com.cidadesinteligentes.infraestructure.exception.BusinessExceptionMessage;
import br.com.cidadesinteligentes.infraestructure.util.ResultError;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.gestor.dto.request.GestorAtualizarRequestDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.gestor.dto.request.GestorCriarRequestDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.gestor.dto.response.GestorResponseDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.gestor.dto.response.GestorRetornarIdResponseDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.gestor.service.IGestorManutencaoService;
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
import java.util.UUID;

/**
 * Controlador REST responsável pelo gerenciamento de gestores de manutenção.
 * <p>
 * Expõe endpoints para criar, listar, atualizar e excluir perfis de gestores.
 * </p>
 *
 * @see IGestorManutencaoService
 * @see GestorResponseDTO
 */
@RestController
@RequestMapping("/api/v1/manutencao-urbana/gestores")
@Tag(name = "Gestão de Gestores", description = "Endpoints para gerenciamento de perfis de gestores de manutenção")
@RequiredArgsConstructor
public class GestorManutencaoController {

    private final IGestorManutencaoService service;

    /**
     * Cria um novo perfil de gestor de manutenção urbana.
     *
     * @param dto    DTO com os dados do gestor.
     * @param result Resultado da validação.
     * @return O gestor criado ou erros de validação.
     */
    @Operation(summary = "Criar Novo Gestor", description = "Cria um perfil de Gestor de Manutenção Urbana.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Gestor criado com sucesso."),
            @ApiResponse(responseCode = "422", description = "Erro de validação: Um ou mais campos na requisição são inválidos.")
    })
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> save(
            @RequestBody @Valid GestorCriarRequestDTO dto,
            BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(ResultError.getResultErrors(result));
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.save(dto));
    }

    /**
     * Recupera todos os gestores cadastrados.
     *
     * @return Lista de gestores.
     */
    @Operation(summary = "Listar Todos os Gestores", description = "Recupera uma lista de todos os gestores cadastrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de gestores recuperada com sucesso.",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = GestorResponseDTO.class))))
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GestorResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    /**
     * Busca um gestor pelo ID.
     *
     * @param id ID (UUID) do gestor.
     * @return Detalhes do gestor.
     */
    @Operation(summary = "Buscar Gestor por ID", description = "Recupera informações detalhadas de um gestor específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Gestor encontrado com sucesso.",
                    content = @Content(schema = @Schema(implementation = GestorResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Gestor não encontrado com o ID fornecido.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<GestorResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    /**
     * Atualiza um gestor existente.
     *
     * @param id     ID do gestor na URL.
     * @param dto    DTO com dados atualizados.
     * @param result Resultado da validação.
     * @return Gestor atualizado.
     */
    @Operation(summary = "Atualizar Gestor", description = "Atualiza as propriedades de um gestor identificado pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Gestor atualizado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Gestor não encontrado."),
            @ApiResponse(responseCode = "422", description = "Erro de validação ou inconsistência de ID.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable UUID id,
            @RequestBody @Valid GestorAtualizarRequestDTO dto,
            BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(ResultError.getResultErrors(result));
        }

        // Validação de consistência: O ID da URL deve ser igual ao do Corpo
        if (!id.equals(dto.id())) {
            throw new BusinessException(BusinessExceptionMessage.ID_MISMATCH.getMessage());
        }

        return ResponseEntity.ok(service.update(dto));
    }

    /**
     * Exclui um gestor pelo ID.
     *
     * @param id ID do gestor.
     * @return ID do gestor excluído.
     */
    @Operation(summary = "Excluir Gestor", description = "Remove um gestor do sistema identificado pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Gestor excluído com sucesso."),
            @ApiResponse(responseCode = "404", description = "Gestor não encontrado.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<GestorRetornarIdResponseDTO> delete(@PathVariable UUID id) {
        UUID idDeletado = service.delete(id);
        return ResponseEntity.ok(new GestorRetornarIdResponseDTO(idDeletado));
    }
}