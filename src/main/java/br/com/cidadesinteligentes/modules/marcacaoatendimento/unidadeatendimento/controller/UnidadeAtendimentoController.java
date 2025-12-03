package br.com.cidadesinteligentes.modules.marcacaoatendimento.unidadeatendimento.controller;

import br.com.cidadesinteligentes.infraestructure.exception.BusinessException;
import br.com.cidadesinteligentes.infraestructure.util.ObjectMapperUtil;
import br.com.cidadesinteligentes.infraestructure.util.ResultError;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.ubs.model.UBS;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.unidadeatendimento.dto.request.UnidadeAtendimentoRequestDTO;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.unidadeatendimento.dto.response.UnidadeAtendimentoResponseDTO;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.unidadeatendimento.model.UnidadeAtendimento;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.unidadeatendimento.service.IUnidadeAtendimentoService;
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
 * Controlador REST responsável pelo gerenciamento de Unidades de Atendimento.
 * <p>
 * Este controller atua como um ponto de entrada polimórfico, capaz de receber DTOs genéricos
 * e instanciar as classes concretas corretas (ex: {@link UBS}) baseando-se no tipo informado.
 * Delega as regras de negócio e persistência para {@link IUnidadeAtendimentoService}.
 * </p>
 *
 * <p><b>Responsabilidades:</b></p>
 * <ul>
 * <li>Receber requisições HTTP e validar o payload (DTO).</li>
 * <li>Converter o DTO para a Entidade Concreta correta (Factory Method).</li>
 * <li>Padronizar as respostas da API.</li>
 * </ul>
 *
 * @author Juan Teles Dias
 * @since 1.0
 * @see IUnidadeAtendimentoService
 */
@RestController
@RequestMapping("/api/v1/unidades-atendimento")
@Tag(name = "Unidade de Atendimento", description = "Endpoints para gestão de UBS e outras unidades de saúde")
@RequiredArgsConstructor
public class UnidadeAtendimentoController {

    private final IUnidadeAtendimentoService service;
    private final ObjectMapperUtil objectMapperUtil;

    /**
     * Cadastra uma nova unidade de atendimento no sistema.
     * <p>
     * O tipo da unidade (ex: "UBS") deve ser informado no DTO para que o sistema
     * instancie a classe correta.
     * </p>
     *
     * @param dto    O DTO contendo os dados cadastrais (nome, endereço, CNES) e o tipo.
     * @param result O resultado da validação dos dados.
     * @return {@link ResponseEntity} com a unidade criada ou erro de validação.
     */
    @Operation(summary = "Salvar Unidade", description = "Cadastra uma nova unidade. O Controller identifica o tipo concreto (ex: UBS) baseado no DTO.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Unidade criada com sucesso.",
                    content = @Content(schema = @Schema(implementation = UnidadeAtendimentoResponseDTO.class))),
            @ApiResponse(responseCode = "422", description = "Erro de validação ou CNES duplicado.")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@RequestBody @Valid UnidadeAtendimentoRequestDTO dto, BindingResult result) {
        if (result.hasErrors()) return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ResultError.getResultErrors(result));

        // Conversão DTO -> Entidade Concreta (Lógica de Factory)
        UnidadeAtendimento entity = converterDtoParaEntidade(dto);

        UnidadeAtendimento savedEntity = service.save(entity);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(objectMapperUtil.mapToRecord(savedEntity, UnidadeAtendimentoResponseDTO.class));
    }

    /**
     * Retorna a lista de todas as unidades de atendimento cadastradas.
     *
     * @return Lista de {@link UnidadeAtendimentoResponseDTO}.
     */
    @Operation(summary = "Listar Todas", description = "Retorna todas as unidades cadastradas, independente do tipo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista recuperada com sucesso.",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = UnidadeAtendimentoResponseDTO.class))))
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UnidadeAtendimentoResponseDTO>> findAll() {
        List<UnidadeAtendimento> entities = service.findAll();

        List<UnidadeAtendimentoResponseDTO> dtos = entities.stream()
                .map(e -> objectMapperUtil.mapToRecord(e, UnidadeAtendimentoResponseDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    /**
     * Busca uma unidade específica pelo seu identificador único.
     *
     * @param id O UUID da unidade.
     * @return Os detalhes da unidade encontrada.
     */
    @Operation(summary = "Buscar por ID", description = "Retorna os detalhes de uma unidade específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Unidade encontrada.",
                    content = @Content(schema = @Schema(implementation = UnidadeAtendimentoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Unidade não encontrada.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UnidadeAtendimentoResponseDTO> findById(@PathVariable UUID id) {
        UnidadeAtendimento entity = service.findById(id);
        return ResponseEntity.ok(objectMapperUtil.mapToRecord(entity, UnidadeAtendimentoResponseDTO.class));
    }

    /**
     * Atualiza os dados cadastrais de uma unidade existente.
     *
     * @param id     O UUID da unidade a ser atualizada.
     * @param dto    Os novos dados da unidade.
     * @param result Resultado da validação.
     * @return A unidade atualizada.
     */
    @Operation(summary = "Atualizar Unidade", description = "Atualiza os dados cadastrais da unidade.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Unidade atualizada com sucesso.",
                    content = @Content(schema = @Schema(implementation = UnidadeAtendimentoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Unidade não encontrada."),
            @ApiResponse(responseCode = "422", description = "Erro de validação.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody @Valid UnidadeAtendimentoRequestDTO dto, BindingResult result) {
        if (result.hasErrors()) return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ResultError.getResultErrors(result));

        // Conversão para entidade (usada como transporte de dados para o update)
        UnidadeAtendimento dadosParaAtualizar = converterDtoParaEntidade(dto);

        UnidadeAtendimento updatedEntity = service.update(id, dadosParaAtualizar);
        return ResponseEntity.ok(objectMapperUtil.mapToRecord(updatedEntity, UnidadeAtendimentoResponseDTO.class));
    }

    /**
     * Remove uma unidade do sistema.
     *
     * @param id O UUID da unidade a ser excluída.
     * @return A unidade que foi removida.
     */
    @Operation(summary = "Deletar Unidade", description = "Remove uma unidade do sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Unidade excluída com sucesso.",
                    content = @Content(schema = @Schema(implementation = UnidadeAtendimentoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Unidade não encontrada."),
            @ApiResponse(responseCode = "400", description = "Erro de integridade (unidade possui serviços ou profissionais vinculados).")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<UnidadeAtendimentoResponseDTO> delete(@PathVariable UUID id) {
        UnidadeAtendimento deletedEntity = service.delete(id);
        return ResponseEntity.ok(objectMapperUtil.mapToRecord(deletedEntity, UnidadeAtendimentoResponseDTO.class));
    }

    // --- Métodos Auxiliares ---

    /**
     * Metodo auxiliar (Factory) para converter o DTO na entidade concreta correta.
     * <p>
     * Necessário pois {@link UnidadeAtendimento} é uma classe abstrata e não pode ser instanciada diretamente.
     * O metodo analisa o campo 'tipoUnidade' do DTO para decidir qual subclasse instanciar.
     * </p>
     *
     * @param dto O DTO de entrada.
     * @return A instância da entidade concreta (ex: UBS).
     * @throws BusinessException Se o tipo informado não for suportado.
     */
    private UnidadeAtendimento converterDtoParaEntidade(UnidadeAtendimentoRequestDTO dto) {
        if ("UBS".equalsIgnoreCase(dto.tipoUnidade())) {
            return objectMapperUtil.map(dto, UBS.class);
        }
        // Futuras expansões:
        // else if ("HOSPITAL".equalsIgnoreCase(dto.tipoUnidade())) { return objectMapperUtil.map(dto, Hospital.class); }

        throw new BusinessException("Tipo de unidade inválido ou não suportado: " + dto.tipoUnidade());
    }
}