package br.com.cidadesinteligentes.modules.marcacaoatendimento.servico.controller;

import br.com.cidadesinteligentes.infraestructure.util.ObjectMapperUtil;
import br.com.cidadesinteligentes.infraestructure.util.ResultError;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.servico.dto.request.ServicoRequestDTO;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.servico.dto.response.ServicoResponseDTO;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.servico.model.Servico;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.servico.service.IServicoService;
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
 * Controlador REST responsável por gerenciar os serviços no sistema.
 * <p>
 * Expõe endpoints para criar, ler, atualizar e excluir serviços.
 * Delega a lógica de negócio para a camada de serviço {@link br.com.cidadesinteligentes.modules.marcacaoatendimento.servico.service.ServicoService}.
 * </p>
 *
 * <p><b>Responsabilidades:</b></p>
 * <ul>
 * <li>Aceitar e validar payloads de serviço.</li>
 * <li>Gerenciar o vínculo entre Serviço e Unidade de Atendimento.</li>
 * <li>Retornar respostas HTTP padronizadas.</li>
 * </ul>
 *
 * @author Juan Teles Dias
 * @since 1.0
 * @see IServicoService
 * @see ServicoResponseDTO
 */
@RestController
@RequestMapping("/api/v1/servicos")
@Tag(name = "Serviço", description = "Endpoints para gestão de serviços oferecidos pelas unidades")
@RequiredArgsConstructor
public class ServicoController {

    private final IServicoService servicoService;
    private final ObjectMapperUtil objectMapperUtil;

    /**
     * Cria um novo {@link Servico} associado a uma Unidade de Atendimento.
     *
     * @param dto    O DTO contendo os dados do serviço (nome e ID da unidade).
     * @param result O detentor do resultado da validação para erros no corpo da requisição.
     * @return Um {@link ResponseEntity} com:
     * <ul>
     * <li><b>201 Created</b> – Quando o serviço é criado com sucesso.</li>
     * <li><b>422 Unprocessable Entity</b> – Quando ocorrem erros de validação no payload.</li>
     * </ul>
     */
    @Operation(summary = "Salvar Serviço", description = "Cadastra um novo serviço vinculado a uma unidade de atendimento.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Criado com sucesso.", content = @Content(schema = @Schema(implementation = ServicoResponseDTO.class))),
            @ApiResponse(responseCode = "422", description = "Erro de validação.")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@RequestBody @Valid ServicoRequestDTO dto, BindingResult result) {
        if (result.hasErrors()) return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ResultError.getResultErrors(result));

        // Conversão DTO -> Entidade
        Servico entity = objectMapperUtil.map(dto, Servico.class);

        // Chama Service
        Servico savedEntity = servicoService.save(entity);

        // Retorna ResponseDTO
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(objectMapperUtil.mapToRecord(savedEntity, ServicoResponseDTO.class));
    }

    /**
     * Recupera todas as entradas de {@link Servico} do sistema.
     *
     * @return Um {@link ResponseEntity} contendo a lista de serviços.
     */
    @Operation(summary = "Listar Todos os Serviços", description = "Retorna uma lista de todos os serviços cadastrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista recuperada com sucesso.",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ServicoResponseDTO.class))))
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ServicoResponseDTO>> findAll() {
        List<Servico> entities = servicoService.findAll();

        List<ServicoResponseDTO> dtos = entities.stream()
                .map(e -> objectMapperUtil.mapToRecord(e, ServicoResponseDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    /**
     * Recupera um {@link Servico} pelo seu identificador único.
     *
     * @param id O UUID do serviço.
     * @return Os detalhes do serviço encontrado.
     */
    @Operation(summary = "Buscar Serviço por ID", description = "Retorna os detalhes de um serviço específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Serviço encontrado.",
                    content = @Content(schema = @Schema(implementation = ServicoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Serviço não encontrado.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ServicoResponseDTO> findById(@PathVariable UUID id) {
        Servico entity = servicoService.findById(id);
        return ResponseEntity.ok(objectMapperUtil.mapToRecord(entity, ServicoResponseDTO.class));
    }

    /**
     * Atualiza um {@link Servico} existente.
     *
     * @param id     O UUID do serviço a ser atualizado.
     * @param dto    O DTO com os novos dados.
     * @param result Resultado da validação.
     * @return O serviço atualizado.
     */
    @Operation(summary = "Atualizar Serviço", description = "Atualiza os dados de um serviço existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Serviço atualizado com sucesso.",
                    content = @Content(schema = @Schema(implementation = ServicoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Serviço não encontrado."),
            @ApiResponse(responseCode = "422", description = "Erro de validação.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody @Valid ServicoRequestDTO dto, BindingResult result) {
        if (result.hasErrors()) return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ResultError.getResultErrors(result));

        Servico dadosParaAtualizar = objectMapperUtil.map(dto, Servico.class);
        Servico updatedEntity = servicoService.update(id, dadosParaAtualizar);

        return ResponseEntity.ok(objectMapperUtil.mapToRecord(updatedEntity, ServicoResponseDTO.class));
    }

    /**
     * Exclui um {@link Servico} do sistema.
     *
     * @param id O UUID do serviço a ser excluído.
     * @return O objeto excluído.
     */
    @Operation(summary = "Deletar Serviço", description = "Remove um serviço do sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Serviço excluído com sucesso.",
                    content = @Content(schema = @Schema(implementation = ServicoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Serviço não encontrado."),
            @ApiResponse(responseCode = "500", description = "Erro de integridade (ex: serviço em uso).")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ServicoResponseDTO> delete(@PathVariable UUID id) {
        Servico deletedEntity = servicoService.delete(id);
        return ResponseEntity.ok(objectMapperUtil.mapToRecord(deletedEntity, ServicoResponseDTO.class));
    }
}