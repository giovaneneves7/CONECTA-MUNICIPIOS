package br.com.cidadesinteligentes.modules.marcacaoatendimento.tipoprofissionalsaude.controller;

import br.com.cidadesinteligentes.infraestructure.util.ObjectMapperUtil;
import br.com.cidadesinteligentes.infraestructure.util.ResultError;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.tipoprofissionalsaude.dto.request.TipoProfissionalSaudeRequestDTO;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.tipoprofissionalsaude.dto.response.TipoProfissionalSaudeResponseDTO;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.tipoprofissionalsaude.model.TipoProfissionalSaude;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.tipoprofissionalsaude.service.ITipoProfissionalSaudeService;
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
import java.util.stream.Collectors;

/**
 * Controlador REST responsável por gerenciar os Tipos de Profissional de Saúde.
 * <p>
 * Expõe endpoints para criar, ler, atualizar e excluir tipos (especialidades ou cargos),
 * como "Médico", "Enfermeiro", "Fisioterapeuta", etc.
 * Delega a lógica de negócio para a camada de serviço {@link ITipoProfissionalSaudeService}.
 * </p>
 *
 * <p><b>Responsabilidades:</b></p>
 * <ul>
 * <li>Receber requisições HTTP e validar o payload (DTO).</li>
 * <li>Converter DTOs para Entidades e vice-versa.</li>
 * <li>Retornar respostas HTTP padronizadas.</li>
 * </ul>
 *
 * @author Juan Teles Dias
 * @since 1.0
 * @see ITipoProfissionalSaudeService
 */
@RestController
@RequestMapping("/api/v1/tipos-profissionais")
@Tag(name = "Tipo de Profissional", description = "Endpoints para cadastro de especialidades/cargos (Ex: Médico, Enfermeiro)")
@RequiredArgsConstructor
public class TipoProfissionalSaudeController {

    private final ITipoProfissionalSaudeService service;
    private final ObjectMapperUtil objectMapperUtil;

    /**
     * Cria um novo Tipo de Profissional de Saúde.
     *
     * @param dto    O DTO contendo os dados do tipo (nome e descrição).
     * @param result O detentor do resultado da validação para erros no corpo da requisição.
     * @return Um {@link ResponseEntity} com:
     * <ul>
     * <li><b>201 Created</b> – Quando o tipo é criado com sucesso.</li>
     * <li><b>422 Unprocessable Entity</b> – Quando ocorrem erros de validação ou duplicidade de nome.</li>
     * </ul>
     */
    @Operation(summary = "Salvar Tipo", description = "Cadastra um novo tipo de profissional.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Criado com sucesso.", content = @Content(schema = @Schema(implementation = TipoProfissionalSaudeResponseDTO.class))),
            @ApiResponse(responseCode = "422", description = "Erro de validação ou nome duplicado.")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@RequestBody @Valid TipoProfissionalSaudeRequestDTO dto, BindingResult result) {
        if (result.hasErrors()) return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ResultError.getResultErrors(result));

        // Conversão DTO -> Entidade
        TipoProfissionalSaude entity = objectMapperUtil.map(dto, TipoProfissionalSaude.class);

        TipoProfissionalSaude savedEntity = service.save(entity);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(objectMapperUtil.mapToRecord(savedEntity, TipoProfissionalSaudeResponseDTO.class));
    }

    /**
     * Recupera todos os Tipos de Profissional cadastrados no sistema.
     *
     * @return Um {@link ResponseEntity} contendo a lista completa de tipos.
     */
    @Operation(summary = "Listar Todos", description = "Retorna todos os tipos cadastrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista recuperada.", content = @Content(array = @ArraySchema(schema = @Schema(implementation = TipoProfissionalSaudeResponseDTO.class))))
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TipoProfissionalSaudeResponseDTO>> findAll() {
        List<TipoProfissionalSaude> entities = service.findAll();

        List<TipoProfissionalSaudeResponseDTO> dtos = entities.stream()
                .map(e -> objectMapperUtil.mapToRecord(e, TipoProfissionalSaudeResponseDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    /**
     * Recupera um Tipo de Profissional pelo seu identificador único.
     *
     * @param id O UUID do tipo a ser recuperado.
     * @return Os detalhes do tipo encontrado.
     */
    @Operation(summary = "Buscar por ID", description = "Retorna os detalhes de um tipo específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Encontrado.", content = @Content(schema = @Schema(implementation = TipoProfissionalSaudeResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Não encontrado.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TipoProfissionalSaudeResponseDTO> findById(@PathVariable UUID id) {
        TipoProfissionalSaude entity = service.findById(id);
        return ResponseEntity.ok(objectMapperUtil.mapToRecord(entity, TipoProfissionalSaudeResponseDTO.class));
    }

    /**
     * Atualiza os dados de um Tipo de Profissional existente.
     *
     * @param id     O UUID do tipo a ser atualizado.
     * @param dto    O DTO com os novos dados.
     * @param result Resultado da validação.
     * @return O objeto atualizado.
     */
    @Operation(summary = "Atualizar Tipo", description = "Atualiza os dados de um tipo existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atualizado.", content = @Content(schema = @Schema(implementation = TipoProfissionalSaudeResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Não encontrado."),
            @ApiResponse(responseCode = "422", description = "Erro de validação.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody @Valid TipoProfissionalSaudeRequestDTO dto, BindingResult result) {
        if (result.hasErrors()) return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ResultError.getResultErrors(result));

        TipoProfissionalSaude dadosParaAtualizar = objectMapperUtil.map(dto, TipoProfissionalSaude.class);

        TipoProfissionalSaude updatedEntity = service.update(id, dadosParaAtualizar);
        return ResponseEntity.ok(objectMapperUtil.mapToRecord(updatedEntity, TipoProfissionalSaudeResponseDTO.class));
    }

    /**
     * Exclui um Tipo de Profissional do sistema.
     *
     * @param id O UUID do tipo a ser excluído.
     * @return O objeto que foi excluído.
     */
    @Operation(summary = "Deletar Tipo", description = "Remove um tipo do sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Excluído com sucesso.", content = @Content(schema = @Schema(implementation = TipoProfissionalSaudeResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Não encontrado."),
            @ApiResponse(responseCode = "400", description = "Erro de integridade (vinculado a profissionais).")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<TipoProfissionalSaudeResponseDTO> delete(@PathVariable UUID id) {
        TipoProfissionalSaude deletedEntity = service.delete(id);
        return ResponseEntity.ok(objectMapperUtil.mapToRecord(deletedEntity, TipoProfissionalSaudeResponseDTO.class));
    }
}