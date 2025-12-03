package br.com.cidadesinteligentes.modules.marcacaoatendimento.profissionalsaude.controller;

import br.com.cidadesinteligentes.infraestructure.util.ObjectMapperUtil;
import br.com.cidadesinteligentes.infraestructure.util.ResultError;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.profissionalsaude.dto.request.ProfissionalSaudeRequestDTO;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.profissionalsaude.dto.response.ProfissionalSaudeResponseDTO;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.profissionalsaude.model.ProfissionalSaude;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.profissionalsaude.service.IProfissionalSaudeService;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.servico.model.Servico;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.tipoprofissionalsaude.model.TipoProfissionalSaude;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.unidadeatendimento.model.UnidadeAtendimento;
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

import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Controlador REST responsável por gerenciar os Profissionais de Saúde.
 * <p>
 * Exponibiliza endpoints para operações de CRUD, lidando com a conversão de DTOs para Entidades
 * e delegando as regras de negócio para a camada de serviço.
 * </p>
 *
 * @author Juan Teles Dias
 * @since 1.0
 * @see IProfissionalSaudeService
 */
@RestController
@RequestMapping("/api/v1/profissionais-saude")
@Tag(name = "Profissional de Saúde", description = "Endpoints para gestão de médicos, enfermeiros e seus vínculos")
@RequiredArgsConstructor
public class ProfissionalSaudeController {

    private final IProfissionalSaudeService service;
    private final ObjectMapperUtil objectMapperUtil;

    /**
     * Cadastra um novo profissional de saúde no sistema.
     *
     * @param dto    Objeto contendo os dados do profissional (CRM, CPF, nome) e seus vínculos (Unidade, Serviços, Tipos).
     * @param result Resultado da validação dos dados de entrada.
     * @return {@link ResponseEntity} com o profissional criado ou erro de validação.
     */
    @Operation(summary = "Salvar Profissional", description = "Cria um novo registro de profissional de saúde, validando unicidade de CRM e vínculos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Profissional criado com sucesso.",
                    content = @Content(schema = @Schema(implementation = ProfissionalSaudeResponseDTO.class))),
            @ApiResponse(responseCode = "422", description = "Erro de validação nos campos ou regra de negócio violada.")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@RequestBody @Valid ProfissionalSaudeRequestDTO dto, BindingResult result) {
        if (result.hasErrors()) return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ResultError.getResultErrors(result));

        // 1. Conversão principal usando ObjectMapper (Padrão Agendamento)
        ProfissionalSaude entity = objectMapperUtil.map(dto, ProfissionalSaude.class);

        // 2. Adaptação das listas de IDs para listas de Objetos (Necessário pois nomes diferem: tiposIds -> tiposProfissional)
        mapListsToEntity(dto, entity);

        // 3. Delegação para o Service
        ProfissionalSaude savedEntity = service.save(entity);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(toResponseDTO(savedEntity));
    }

    /**
     * Retorna a lista completa de profissionais cadastrados.
     *
     * @return Lista de {@link ProfissionalSaudeResponseDTO}.
     */
    @Operation(summary = "Listar Todos", description = "Recupera todos os profissionais de saúde cadastrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista recuperada com sucesso.",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProfissionalSaudeResponseDTO.class))))
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProfissionalSaudeResponseDTO>> findAll() {
        List<ProfissionalSaude> entities = service.findAll();
        List<ProfissionalSaudeResponseDTO> dtos = entities.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    /**
     * Busca um profissional pelo seu identificador único (UUID).
     *
     * @param id Identificador do profissional.
     * @return Dados detalhados do profissional.
     */
    @Operation(summary = "Buscar por ID", description = "Recupera os detalhes de um profissional específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profissional encontrado.",
                    content = @Content(schema = @Schema(implementation = ProfissionalSaudeResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Profissional não encontrado.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProfissionalSaudeResponseDTO> findById(@PathVariable UUID id) {
        ProfissionalSaude entity = service.findById(id);
        return ResponseEntity.ok(toResponseDTO(entity));
    }

    /**
     * Atualiza os dados de um profissional existente.
     *
     * @param id     Identificador do profissional a ser atualizado.
     * @param dto    Novos dados do profissional.
     * @param result Resultado da validação.
     * @return Profissional atualizado.
     */
    @Operation(summary = "Atualizar Profissional", description = "Atualiza dados cadastrais, unidade vinculada, serviços e tipos de um profissional.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profissional atualizado com sucesso.",
                    content = @Content(schema = @Schema(implementation = ProfissionalSaudeResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Profissional não encontrado."),
            @ApiResponse(responseCode = "422", description = "Erro de validação.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody @Valid ProfissionalSaudeRequestDTO dto, BindingResult result) {
        if (result.hasErrors()) return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ResultError.getResultErrors(result));

        // Conversão e Adaptação
        ProfissionalSaude dadosParaAtualizar = objectMapperUtil.map(dto, ProfissionalSaude.class);
        mapListsToEntity(dto, dadosParaAtualizar);

        ProfissionalSaude updatedEntity = service.update(id, dadosParaAtualizar);
        return ResponseEntity.ok(toResponseDTO(updatedEntity));
    }

    /**
     * Remove um profissional do sistema.
     *
     * @param id Identificador do profissional.
     * @return Profissional removido.
     */
    @Operation(summary = "Deletar Profissional", description = "Realiza a exclusão de um profissional, desde que não viole integridade referencial.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profissional excluído com sucesso.",
                    content = @Content(schema = @Schema(implementation = ProfissionalSaudeResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Profissional não encontrado."),
            @ApiResponse(responseCode = "400", description = "Não é possível excluir (ex: existem agendamentos vinculados).")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ProfissionalSaudeResponseDTO> delete(@PathVariable UUID id) {
        ProfissionalSaude deletedEntity = service.delete(id);
        return ResponseEntity.ok(toResponseDTO(deletedEntity));
    }

    // --- Métodos de Apoio (Mapping / Factory) ---

    /**
     * Realiza o mapeamento manual complementar das listas de IDs para listas de Objetos (Proxy).
     * Necessário pois o ObjectMapper padrão não associa automaticamente "tiposIds" (DTO) com "tiposProfissional" (Entity).
     *
     * @param dto    DTO de origem.
     * @param entity Entidade de destino (já instanciada pelo ObjectMapper).
     */
    private void mapListsToEntity(ProfissionalSaudeRequestDTO dto, ProfissionalSaude entity) {
        // Mapeamento Unidade (N:1) - Caso o ObjectMapper não tenha resolvido unidadeId -> unidadeVinculada
        if (dto.unidadeId() != null && entity.getUnidadeVinculada() == null) {
            UnidadeAtendimento unidadeProxy = new br.com.cidadesinteligentes.modules.marcacaoatendimento.ubs.model.UBS();
            setEntityId(unidadeProxy, dto.unidadeId());
            entity.setUnidadeVinculada(unidadeProxy);
        }

        // Mapeamento Tipos (N:N)
        if (dto.tiposIds() != null && !dto.tiposIds().isEmpty()) {
            List<TipoProfissionalSaude> tipos = dto.tiposIds().stream().map(id -> {
                TipoProfissionalSaude t = new TipoProfissionalSaude();
                setEntityId(t, id);
                return t;
            }).collect(Collectors.toList());
            entity.setTiposProfissional(tipos);
        }

        // Mapeamento Serviços (N:N)
        if (dto.servicosIds() != null && !dto.servicosIds().isEmpty()) {
            List<Servico> servicos = dto.servicosIds().stream().map(id -> {
                Servico s = new Servico();
                setEntityId(s, id);
                return s;
            }).collect(Collectors.toList());
            entity.setServicosOferecidos(servicos);
        }
    }

    /**
     * Converte a Entidade completa para o DTO de Resposta.
     */
    private ProfissionalSaudeResponseDTO toResponseDTO(ProfissionalSaude entity) {
        return new ProfissionalSaudeResponseDTO(
                entity.getId(),
                entity.getUser().getPerson().getFullName(),
                entity.getUser().getPerson().getCpf(),
                entity.getUser().getEmail(),
                entity.getCrm(),
                entity.getUnidadeVinculada() != null ? entity.getUnidadeVinculada().getId() : null,
                entity.getUnidadeVinculada() != null ? entity.getUnidadeVinculada().getNome() : null,
                entity.getTiposProfissional() != null ? entity.getTiposProfissional().stream().map(TipoProfissionalSaude::getNome).collect(Collectors.toList()) : List.of(),
                entity.getServicosOferecidos() != null ? entity.getServicosOferecidos().stream().map(Servico::getNome).collect(Collectors.toList()) : List.of()
        );
    }

    /**
     * Utilitário para definir o ID em entidades onde o metodo setId() não é visível.
     * Garante que o objeto Proxy tenha o ID correto para o JPA fazer o vínculo.
     */
    private void setEntityId(Object entity, UUID id) {
        try {
            Class<?> clazz = entity.getClass();
            while (clazz != null) {
                try {
                    Field field = clazz.getDeclaredField("id");
                    field.setAccessible(true);
                    field.set(entity, id);
                    return;
                } catch (NoSuchFieldException e) {
                    clazz = clazz.getSuperclass();
                }
            }
        } catch (Exception e) {
            // Em produção, logs de erro seriam apropriados aqui
            throw new RuntimeException("Falha de mapeamento interno: ID não pode ser definido.", e);
        }
    }
}
