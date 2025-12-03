package br.com.cidadesinteligentes.modules.marcacaoatendimento.disponibilidadeprofissional.controller;

import br.com.cidadesinteligentes.infraestructure.util.ObjectMapperUtil;
import br.com.cidadesinteligentes.infraestructure.util.ResultError;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.disponibilidadeprofissional.dto.request.DisponibilidadeProfissionalRequestDTO;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.disponibilidadeprofissional.dto.response.DisponibilidadeProfissionalResponseDTO;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.disponibilidadeprofissional.model.DisponibilidadeProfissional;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.disponibilidadeprofissional.service.IDisponibilidadeProfissionalService;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.profissionalsaude.model.ProfissionalSaude;
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
 * Controlador REST responsável pelo gerenciamento da Disponibilidade (Grade de Horários) dos Profissionais.
 * <p>
 * Expõe endpoints para adicionar e remover horários de atendimento, servindo como base para a validação
 * de agendamentos. Delega a lógica de negócio para {@link IDisponibilidadeProfissionalService}.
 * </p>
 *
 * @author Juan Teles Dias
 * @since 1.0
 * @see IDisponibilidadeProfissionalService
 */
@RestController
@RequestMapping("/api/v1/disponibilidades")
@Tag(name = "Disponibilidade Profissional", description = "Endpoints para gestão da grade de horários dos médicos")
@RequiredArgsConstructor
public class DisponibilidadeProfissionalController {

    private final IDisponibilidadeProfissionalService service;
    private final ObjectMapperUtil objectMapperUtil;

    /**
     * Cadastra um novo horário de disponibilidade na grade de um profissional.
     *
     * @param dto    Objeto contendo dia da semana, hora início, hora fim e ID do profissional.
     * @param result Resultado da validação dos campos.
     * @return {@link ResponseEntity} contendo o registro criado ou erros de validação.
     */
    @Operation(summary = "Adicionar Horário", description = "Cadastra um novo intervalo de disponibilidade para um profissional, validando sobreposições.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Disponibilidade criada com sucesso.",
                    content = @Content(schema = @Schema(implementation = DisponibilidadeProfissionalResponseDTO.class))),
            @ApiResponse(responseCode = "422", description = "Erro de validação (ex: Hora fim menor que início, conflito de horário).")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@RequestBody @Valid DisponibilidadeProfissionalRequestDTO dto, BindingResult result) {
        if (result.hasErrors()) return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ResultError.getResultErrors(result));

        // Conversão DTO -> Entidade (isolada em método auxiliar)
        DisponibilidadeProfissional entity = mapDtoToEntity(dto);

        DisponibilidadeProfissional savedEntity = service.save(entity);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(toResponseDTO(savedEntity));
    }

    /**
     * Retorna a lista completa de todas as disponibilidades cadastradas no sistema.
     *
     * @return Lista de {@link DisponibilidadeProfissionalResponseDTO}.
     */
    @Operation(summary = "Listar Todas", description = "Retorna toda a grade de horários cadastrada no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista recuperada com sucesso.",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = DisponibilidadeProfissionalResponseDTO.class))))
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DisponibilidadeProfissionalResponseDTO>> findAll() {
        List<DisponibilidadeProfissional> entities = service.findAll();
        List<DisponibilidadeProfissionalResponseDTO> dtos = entities.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    /**
     * Busca um registro de disponibilidade específico pelo seu ID.
     *
     * @param id O UUID da disponibilidade.
     * @return Os detalhes do horário cadastrado.
     */
    @Operation(summary = "Buscar por ID", description = "Retorna detalhes de um registro de disponibilidade específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro encontrado.",
                    content = @Content(schema = @Schema(implementation = DisponibilidadeProfissionalResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<DisponibilidadeProfissionalResponseDTO> findById(@PathVariable UUID id) {
        DisponibilidadeProfissional entity = service.findById(id);
        return ResponseEntity.ok(toResponseDTO(entity));
    }

    /**
     * Remove um horário da grade de disponibilidade.
     *
     * @param id O UUID do registro a ser removido.
     * @return O registro que foi excluído.
     */
    @Operation(summary = "Remover Horário", description = "Remove um horário da grade de um profissional.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro excluído com sucesso.",
                    content = @Content(schema = @Schema(implementation = DisponibilidadeProfissionalResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<DisponibilidadeProfissionalResponseDTO> delete(@PathVariable UUID id) {
        DisponibilidadeProfissional deletedEntity = service.delete(id);
        return ResponseEntity.ok(toResponseDTO(deletedEntity));
    }

    // --- Métodos Auxiliares (Factory/Mapper) ---

    /**
     * Converte o RequestDTO para Entidade, tratando o vínculo com ProfissionalSaude via Proxy.
     */
    private DisponibilidadeProfissional mapDtoToEntity(DisponibilidadeProfissionalRequestDTO dto) {
        DisponibilidadeProfissional entity = objectMapperUtil.map(dto, DisponibilidadeProfissional.class);

        if (dto.profissionalId() != null) {
            ProfissionalSaude profProxy = new ProfissionalSaude();
            setEntityId(profProxy, dto.profissionalId());
            entity.setProfissional(profProxy);
        }

        return entity;
    }

    private DisponibilidadeProfissionalResponseDTO toResponseDTO(DisponibilidadeProfissional entity) {
        return new DisponibilidadeProfissionalResponseDTO(
                entity.getId(),
                entity.getDia(),
                entity.getHoraInicio(),
                entity.getHoraFim(),
                entity.getProfissional() != null ? entity.getProfissional().getId() : null,
                entity.getProfissional() != null ? entity.getProfissional().getUser().getPerson().getFullName() : null
        );
    }

    /**
     * Utilitário para definir ID em entidades onde o método setId não é visível (PersistenceEntity).
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
            throw new RuntimeException("Erro ao definir ID via Reflection.", e);
        }
    }
}