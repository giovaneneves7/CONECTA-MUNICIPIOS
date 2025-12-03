package br.com.cidadesinteligentes.modules.marcacaoatendimento.agendamento.controller;

import br.com.cidadesinteligentes.infraestructure.util.ObjectMapperUtil;
import br.com.cidadesinteligentes.infraestructure.util.ResultError;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.agendamento.dto.request.AgendamentoRequestDTO;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.agendamento.dto.request.AgendamentoStatusUpdateDTO;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.agendamento.dto.request.AgendamentoUpdateDTO;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.agendamento.dto.response.AgendamentoResponseDTO;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.agendamento.model.Agendamento;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.agendamento.service.IAgendamentoService;
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
 * Controlador REST responsável por gerenciar os agendamentos no sistema.
 * <p>
 * Expõe endpoints para criar, ler, atualizar (reagendar), alterar status e cancelar agendamentos.
 * Delega a lógica de negócio para a camada de serviço {@link br.com.cidadesinteligentes.modules.marcacaoatendimento.agendamento.service.AgendamentoService} enquanto lida com as requisições e respostas HTTP.
 * </p>
 *
 * <p><b>Responsabilidades:</b></p>
 * <ul>
 * <li>Aceitar e validar payloads de agendamento.</li>
 * <li>Delegar operações relacionadas a agendamento para a camada de serviço.</li>
 * <li>Retornar respostas HTTP padronizadas com os códigos de status apropriados.</li>
 * </ul>
 *
 * @author Juan Teles Dias
 * @since 1.0
 * @see IAgendamentoService
 * @see AgendamentoResponseDTO
 */
@RestController
@RequestMapping("/api/v1/agendamentos")
@Tag(name = "Agendamento", description = "Endpoints para gestão de marcação de consultas")
@RequiredArgsConstructor
public class AgendamentoController {

    private final IAgendamentoService agendamentoService;
    private final ObjectMapperUtil objectMapperUtil;

    /**
     * Cria um novo {@link Agendamento} associado a um paciente, profissional e serviço.
     *
     * @param dto    O DTO contendo os dados do agendamento para persistir.
     * @param result O detentor do resultado da validação para erros no corpo da requisição.
     * @return Um {@link ResponseEntity} com:
     * <ul>
     * <li><b>201 Created</b> – Quando o agendamento é criado com sucesso.</li>
     * <li><b>404 Not Found</b> – Quando as entidades relacionadas (paciente, profissional, serviço) não são encontradas.</li>
     * <li><b>422 Unprocessable Entity</b> – Quando ocorrem erros de validação no corpo da requisição.</li>
     * </ul>
     */
    @Operation(summary = "Salvar Agendamento", description = "Cria um novo agendamento de consulta para um paciente com um profissional e serviço específicos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Criado com sucesso.", content = @Content(schema = @Schema(implementation = AgendamentoResponseDTO.class))),
            @ApiResponse(responseCode = "422", description = "Erro de validação.")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@RequestBody @Valid AgendamentoRequestDTO dto, BindingResult result) {
        if (result.hasErrors()) return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ResultError.getResultErrors(result));

        //  Conversão DTO -> Entidade usando ObjectMapperUtil
        Agendamento entity = objectMapperUtil.map(dto, Agendamento.class);

        //  Chama Service
        Agendamento savedEntity = agendamentoService.save(entity);

        //  Conversão Entidade -> DTO Response
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(objectMapperUtil.mapToRecord(savedEntity, AgendamentoResponseDTO.class));
    }

    /**
     * Recupera todas as entradas de {@link Agendamento} do sistema.
     *
     * @return Um {@link ResponseEntity} com:
     * <ul>
     * <li><b>200 OK</b> – Quando a lista de agendamentos é recuperada com sucesso.</li>
     * </ul>
     */
    @Operation(summary = "Listar Todos os Agendamentos", description = "Retorna uma lista de todos os agendamentos registrados no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista recuperada com sucesso.",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = AgendamentoResponseDTO.class))))
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AgendamentoResponseDTO>> findAll() {
        List<Agendamento> entities = agendamentoService.findAll();

        List<AgendamentoResponseDTO> dtos = entities.stream()
                .map(e -> objectMapperUtil.mapToRecord(e, AgendamentoResponseDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    /**
     * Recupera um {@link Agendamento} pelo seu identificador único (UUID).
     *
     * @param id O UUID do agendamento a ser recuperado.
     * @return Um {@link ResponseEntity} com:
     * <ul>
     * <li><b>200 OK</b> – Quando o agendamento é encontrado.</li>
     * <li><b>404 Not Found</b> – Quando nenhum agendamento existe com o UUID fornecido.</li>
     * </ul>
     */
    @Operation(summary = "Buscar Agendamento por ID", description = "Retorna os detalhes de um agendamento específico identificado pelo seu UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agendamento encontrado.",
                    content = @Content(schema = @Schema(implementation = AgendamentoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AgendamentoResponseDTO> findById(@PathVariable UUID id) {
        Agendamento entity = agendamentoService.findById(id);
        return ResponseEntity.ok(objectMapperUtil.mapToRecord(entity, AgendamentoResponseDTO.class));
    }

    /**
     * Atualiza um {@link Agendamento} existente identificado pelo seu UUID.
     * <p>
     * Utilizado para reagendar consultas (alterar data e hora).
     * </p>
     *
     * @param id     O UUID do agendamento a ser atualizado.
     * @param dto    O DTO contendo os dados atualizados do agendamento.
     * @param result O detentor do resultado da validação para erros no corpo da requisição.
     * @return Um {@link ResponseEntity} com:
     * <ul>
     * <li><b>200 OK</b> – Quando o agendamento é atualizado com sucesso.</li>
     * <li><b>404 Not Found</b> – Quando o agendamento com o UUID fornecido não existe.</li>
     * <li><b>422 Unprocessable Entity</b> – Quando ocorrem erros de validação.</li>
     * </ul>
     */
    @Operation(summary = "Atualizar Agendamento (Reagendar)", description = "Atualiza os dados (data/hora) de um agendamento existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agendamento atualizado com sucesso.",
                    content = @Content(schema = @Schema(implementation = AgendamentoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado."),
            @ApiResponse(responseCode = "422", description = "Erro de validação (ex: data no passado).")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody @Valid AgendamentoUpdateDTO dto, BindingResult result) {
        if (result.hasErrors()) return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ResultError.getResultErrors(result));

        // Mapeamento simples pois UpdateDTO só tem campos primitivos (data/hora)
        Agendamento dadosParaAtualizar = objectMapperUtil.map(dto, Agendamento.class);

        Agendamento updatedEntity = agendamentoService.update(id, dadosParaAtualizar);

        return ResponseEntity.ok(objectMapperUtil.mapToRecord(updatedEntity, AgendamentoResponseDTO.class));
    }

    /**
     * Atualiza o status de um {@link Agendamento}.
     *
     * @param id     O UUID do agendamento a ser atualizado.
     * @param dto    O DTO contendo o novo status.
     * @param result O detentor do resultado da validação para erros no corpo da requisição.
     * @return Um {@link ResponseEntity} com:
     * <ul>
     * <li><b>200 OK</b> – Quando o status do agendamento é atualizado com sucesso.</li>
     * <li><b>404 Not Found</b> – Quando o agendamento com o UUID fornecido não existe.</li>
     * <li><b>422 Unprocessable Entity</b> – Quando ocorrem erros de validação.</li>
     * </ul>
     */
    @Operation(summary = "Atualizar Status do Agendamento", description = "Atualiza somente o status do agendamento (ex: REALIZADO, AUSENTE).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso.",
                    content = @Content(schema = @Schema(implementation = AgendamentoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Status inválido ou transição não permitida."),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado.")
    })
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable UUID id, @RequestBody @Valid AgendamentoStatusUpdateDTO dto, BindingResult result) {
        if (result.hasErrors()) return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ResultError.getResultErrors(result));

        Agendamento updatedEntity = agendamentoService.updateStatus(id, dto.status());
        return ResponseEntity.ok(objectMapperUtil.mapToRecord(updatedEntity, AgendamentoResponseDTO.class));
    }

    /**
     * Exclui (cancela logicamente) um {@link Agendamento} pelo seu UUID.
     *
     * @param id O UUID do agendamento a ser excluído.
     * @return Um {@link ResponseEntity} com:
     * <ul>
     * <li><b>200 OK</b> – Quando o agendamento é excluído com sucesso (retorna o objeto com status CANCELADO).</li>
     * <li><b>404 Not Found</b> – Quando nenhum agendamento existe com o UUID fornecido.</li>
     * </ul>
     */
    @Operation(summary = "Deletar Agendamento (Cancelar)", description = "Realiza a exclusão lógica (cancelamento) do agendamento.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agendamento cancelado com sucesso.",
                    content = @Content(schema = @Schema(implementation = AgendamentoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Agendamento já realizado ou já cancelado."),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<AgendamentoResponseDTO> delete(@PathVariable UUID id) {
        Agendamento deletedEntity = agendamentoService.delete(id);
        return ResponseEntity.ok(objectMapperUtil.mapToRecord(deletedEntity, AgendamentoResponseDTO.class));
    }
}