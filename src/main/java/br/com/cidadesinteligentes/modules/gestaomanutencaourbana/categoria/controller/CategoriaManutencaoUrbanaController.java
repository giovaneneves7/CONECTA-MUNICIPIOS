package br.com.cidadesinteligentes.modules.gestaomanutencaourbana.categoria.controller;

import br.com.cidadesinteligentes.infraestructure.exception.BusinessException;
import br.com.cidadesinteligentes.infraestructure.exception.BusinessExceptionMessage;
import br.com.cidadesinteligentes.infraestructure.util.ResultError;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.categoria.dto.request.CategoriaAtualizarRequestDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.categoria.dto.request.CategoriaCriarRequestDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.categoria.dto.response.CategoriaResponseDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.categoria.dto.response.CategoriaRetornarIdResponseDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.categoria.service.ICategoriaManutencaoUrbanaService;
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
 * Controlador REST responsável pelo gerenciamento das categorias de manutenção urbana.
 * <p>
 * Expõe endpoints para criar, listar, atualizar e excluir categorias.
 * Delega a lógica de negócios para a camada de serviço de domínio.
 * </p>
 *
 * @author Jeovani
 * @since 1.0
 * @see ICategoriaManutencaoUrbanaService
 * @see CategoriaResponseDTO
 */
@RestController
@RequestMapping("/api/v1/manutencao-urbana/categorias")
@Tag(name = "Gestão de Categorias", description = "Endpoints para gerenciamento das categorias de manutenção urbana")
@RequiredArgsConstructor
public class CategoriaManutencaoUrbanaController {

    private final ICategoriaManutencaoUrbanaService categoriaService;

    /**
     * Cria uma nova categoria de manutenção urbana.
     *
     * @param categoriaDto O DTO contendo os dados da categoria a ser persistida.
     * @param result       Resultado da validação para erros no corpo da requisição.
     * @return Um {@link ResponseEntity} com status 201 Created ou 422 Unprocessable Entity.
     */
    @Operation(summary = "Criar Nova Categoria", description = "Cria uma nova categoria de manutenção urbana no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso."),
            @ApiResponse(responseCode = "422", description = "Erro de validação: Um ou mais campos na requisição são inválidos.")
    })
    @PostMapping(value = "/categoria",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> save(
            @RequestBody @Valid CategoriaCriarRequestDTO categoriaDto,
            BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(ResultError.getResultErrors(result));
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoriaService.save(categoriaDto));
    }

    /**
     * Recupera todas as categorias de manutenção urbana cadastradas.
     *
     * @return Uma lista de categorias.
     */
    @Operation(summary = "Listar Todas as Categorias", description = "Recupera uma lista de todas as categorias de manutenção armazenadas no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de categorias recuperada com sucesso.",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CategoriaResponseDTO.class))))
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CategoriaResponseDTO>> findAll() {
        return ResponseEntity.ok(categoriaService.findAll());
    }

    /**
     * Recupera uma categoria pelo seu ID.
     *
     * @param id O ID da categoria a ser recuperada.
     * @return Os detalhes da categoria.
     */
    @Operation(summary = "Buscar Categoria por ID", description = "Recupera informações detalhadas de uma categoria específica identificada pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria encontrada com sucesso.",
                    content = @Content(schema = @Schema(implementation = CategoriaResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada com o ID fornecido.")
    })
    @GetMapping("/categoria/{id}")
    public ResponseEntity<CategoriaResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(categoriaService.findById(id));
    }

    /**
     * Atualiza uma categoria existente.
     *
     * @param id           O ID da categoria a ser atualizada.
     * @param categoriaDto O DTO contendo os dados atualizados.
     * @param result       Resultado da validação.
     * @return A categoria atualizada.
     */
    @Operation(summary = "Atualizar Categoria", description = "Atualiza as propriedades de uma categoria identificada pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso."),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada."),
            @ApiResponse(responseCode = "422", description = "Erro de validação: Dados inválidos.")
    })
    @PutMapping("/categoria/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestBody @Valid CategoriaAtualizarRequestDTO categoriaDto,
            BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(ResultError.getResultErrors(result));
        }

        // Validação de consistência: O ID da URL deve ser igual ao do Corpo
        if (!id.equals(categoriaDto.id())) {
            throw new BusinessException(BusinessExceptionMessage.ID_MISMATCH.getMessage());
        }

        return ResponseEntity.ok(categoriaService.update(categoriaDto));
    }

    /**
     * Exclui uma categoria pelo seu ID.
     *
     * @param id O ID da categoria a ser excluída.
     * @return O DTO contendo o ID da categoria excluída.
     */
    @Operation(summary = "Excluir Categoria", description = "Remove uma categoria do sistema identificada pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria excluída com sucesso."),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada.")
    })
    @DeleteMapping("/categoria/{id}")
    public ResponseEntity<CategoriaRetornarIdResponseDTO> excluir(@PathVariable Long id) {
        // Recebe o ID do service (Long)
        Long idDeletado = categoriaService.delete(id);

        // Retorna embrulhado no DTO: { "id": 1 }
        return ResponseEntity.ok(new CategoriaRetornarIdResponseDTO(idDeletado));
    }
}