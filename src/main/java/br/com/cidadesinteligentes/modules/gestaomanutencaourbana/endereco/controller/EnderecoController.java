package br.com.cidadesinteligentes.modules.gestaomanutencaourbana.endereco.controller;

import br.com.cidadesinteligentes.infraestructure.exception.BusinessException;
import br.com.cidadesinteligentes.infraestructure.exception.BusinessExceptionMessage;
import br.com.cidadesinteligentes.infraestructure.util.ResultError;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.endereco.dto.request.EnderecoAtualizarRequestDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.endereco.dto.request.EnderecoCriarRequestDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.endereco.dto.response.EnderecoResponseDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.endereco.dto.response.EnderecoRetornarIdResponseDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.endereco.service.IEnderecoService;
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
 * Controlador REST responsável pelo gerenciamento de endereços.
 * <p>
 * Expõe endpoints para criar, listar, atualizar e excluir endereços de manutenção urbana.
 * </p>
 *
 * @see IEnderecoService
 * @see EnderecoResponseDTO
 */
@RestController
@RequestMapping("/api/v1/manutencao-urbana/enderecos")
@Tag(name = "Gestão de Endereços", description = "Endpoints para gerenciamento dos endereços de manutenção urbana")
@RequiredArgsConstructor
public class EnderecoController {

    private final IEnderecoService service;

    /**
     * Cadastra um novo endereço.
     *
     * @param dto    DTO com os dados do endereço.
     * @param result Resultado da validação.
     * @return O endereço criado ou erros de validação.
     */
    @Operation(summary = "Criar Novo Endereço", description = "Cria um novo registro de endereço no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Endereço criado com sucesso."),
            @ApiResponse(responseCode = "422", description = "Erro de validação: Um ou mais campos na requisição são inválidos.")
    })
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> save(
            @RequestBody @Valid EnderecoCriarRequestDTO dto,
            BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(ResultError.getResultErrors(result));
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.save(dto));
    }

    /**
     * Recupera todos os endereços cadastrados.
     *
     * @return Lista de endereços.
     */
    @Operation(summary = "Listar Todos os Endereços", description = "Recupera uma lista de todos os endereços cadastrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de endereços recuperada com sucesso.",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = EnderecoResponseDTO.class))))
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EnderecoResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    /**
     * Busca um endereço pelo ID.
     *
     * @param id ID do endereço.
     * @return Detalhes do endereço.
     */
    @Operation(summary = "Buscar Endereço por ID", description = "Recupera informações detalhadas de um endereço específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endereço encontrado com sucesso.",
                    content = @Content(schema = @Schema(implementation = EnderecoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado com o ID fornecido.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EnderecoResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    /**
     * Atualiza um endereço existente.
     *
     * @param id     ID do endereço na URL.
     * @param dto    DTO com dados atualizados.
     * @param result Resultado da validação.
     * @return Endereço atualizado.
     */
    @Operation(summary = "Atualizar Endereço", description = "Atualiza as propriedades de um endereço identificado pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endereço atualizado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado."),
            @ApiResponse(responseCode = "422", description = "Erro de validação ou inconsistência de ID.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestBody @Valid EnderecoAtualizarRequestDTO dto,
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
     * Exclui um endereço pelo ID.
     *
     * @param id ID do endereço.
     * @return ID do endereço excluído.
     */
    @Operation(summary = "Excluir Endereço", description = "Remove um endereço do sistema identificado pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endereço excluído com sucesso."),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<EnderecoRetornarIdResponseDTO> delete(@PathVariable Long id) {
        Long idDeletado = service.delete(id);
        return ResponseEntity.ok(new EnderecoRetornarIdResponseDTO(idDeletado));
    }
}