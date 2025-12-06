package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.controller;

import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.requerimentoalvaraconstrucao.dto.request.RejectionRequestDTO;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.requerimentoalvaraconstrucao.enums.AssociationStatus;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.requerimentoalvaraconstrucao.service.ConstructionLicenseRequirementService;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.documento.dto.response.DocumentResponseDTO;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.documento.dto.response.DocumentWithStatusResponseDTO;
import br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.documento.enums.DocumentStatus;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.dto.request.SolicitacaoRequestDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.dto.request.SolicitacaoAnaliseRequestDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.dto.request.SolicitacaoAtualizacaoRequestDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.dto.request.SolicitacaoFinalizadaRequestDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.dto.response.SolicitacaoResponseDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.dto.response.SolicitacaoDetalhadaResponseDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.dto.response.SolicitacaoSimplesResponseDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.service.ISolicitacaoService;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.atualizacao.dto.response.AtualizacaoResponseDTO;
import br.com.cidadesinteligentes.infraestructure.util.ObjectMapperUtil;
import br.com.cidadesinteligentes.infraestructure.util.ResultError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller responsável por manipular os endpoints de Solicitação.
 * Fornece operações para criar, listar, recuperar, atualizar e excluir solicitações.
 *
 * @author Caio Alves, Andesson Reis
 */
@RestController
@RequestMapping("/api/v1/solicitacoes")
@RequiredArgsConstructor
public class SolicitacaoController {

  private final ObjectMapperUtil objectMapperUtil;
  private final ISolicitacaoService solicitacaoService;
  private final ConstructionLicenseRequirementService requerimentoAlvaraConstrucaoService;

  /**
   * Endpoint para criar uma nova solicitação.
   *
   * @param dto DTO contendo os dados da solicitação.
   * @return Resposta com os dados da solicitação criada.
   */
  @Operation(summary = "Criar uma nova Solicitação", description = "Cria e persiste uma nova solicitação no sistema.")
  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "Solicitação criada com sucesso",
        content = @Content(schema = @Schema(implementation = SolicitacaoResponseDTO.class))
      ),
      @ApiResponse(responseCode = "400", description = "Corpo da requisição inválido"),
      @ApiResponse(responseCode = "422", description = "Um ou mais campos são inválidos"),
    }
  )
  @PostMapping(path = "/solicitacao")
  public ResponseEntity<?> criar(@RequestBody @Valid SolicitacaoRequestDTO dto, BindingResult result) {
    return result.hasErrors()
      ? ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResultError.getResultErrors(result))
      : ResponseEntity.ok(solicitacaoService.save(dto));
  }

  /**
   * Endpoint para listar todas as solicitações.
   *
   * @return Lista de todas as solicitações registradas.
   */
  @Operation(summary = "Listar todas as Solicitações", description = "Recupera uma lista de todas as solicitações registradas.")
  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "Lista de solicitações recuperada",
        content = @Content(array = @ArraySchema(schema = @Schema(implementation = SolicitacaoResponseDTO.class)))
      ),
    }
  )
  @GetMapping
  public ResponseEntity<List<SolicitacaoResponseDTO>> listarTodas(@PageableDefault(size = 10, sort = "dataCriacao", direction = Sort.Direction.DESC) Pageable pageable) {
    return ResponseEntity.ok(solicitacaoService.findAll(pageable));
  }

  /**
   * Endpoint para recuperar uma solicitação pelo seu ID.
   *
   * @param id UUID da solicitação.
   * @return Dados da solicitação se encontrada, caso contrário 404.
   */
  @Operation(summary = "Buscar Solicitação por ID", description = "Busca os detalhes de uma solicitação pelo seu UUID.")
  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "Solicitação encontrada",
        content = @Content(schema = @Schema(implementation = SolicitacaoResponseDTO.class))
      ),
      @ApiResponse(responseCode = "404", description = "Solicitação não encontrada"),
    }
  )
  @GetMapping("/solicitacao/{id}")
  public ResponseEntity<SolicitacaoResponseDTO> buscarPorId(@Valid @PathVariable UUID id) {
    return ResponseEntity.status(HttpStatus.OK).body(solicitacaoService.findById(id));
  }

  /**
   * Endpoint para atualizar uma solicitação existente pelo seu ID.
   *
   * @param id  UUID da solicitação.
   * @param dto DTO contendo os dados atualizados da solicitação.
   * @return Dados da solicitação atualizada.
   */
  @Operation(
    summary = "Atualizar uma Solicitação existente",
    description = "Atualiza uma solicitação substituindo seus dados pelo payload fornecido."
  )
  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "Solicitação atualizada com sucesso",
        content = @Content(schema = @Schema(implementation = SolicitacaoResponseDTO.class))
      ),
      @ApiResponse(responseCode = "404", description = "Solicitação não encontrada"),
      @ApiResponse(responseCode = "422", description = "Um ou mais campos são inválidos"),
    }
  )
  @PutMapping("/solicitacao/{id}")
  public ResponseEntity<?> atualizar(
    @Valid @PathVariable UUID id,
    @RequestBody SolicitacaoAtualizacaoRequestDTO dto,
    BindingResult result
  ) {
    return result.hasErrors()
      ? ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResultError.getResultErrors(result))
      : ResponseEntity.ok(solicitacaoService.update(id, dto));
  }

  /**3
   * Endpoint para excluir uma solicitação pelo seu ID.
   *
   * @param id UUID da solicitação.
   * @return Sem conteúdo se a exclusão for bem-sucedida.
   */
  @Operation(summary = "Excluir uma Solicitação", description = "Exclui uma solicitação pelo seu UUID.")
  @ApiResponses(
    value = {
      @ApiResponse(responseCode = "204", description = "Solicitação excluída com sucesso"),
      @ApiResponse(responseCode = "404", description = "Solicitação não encontrada"),
    }
  )
  @DeleteMapping("/solicitacao/{id}")
  public ResponseEntity<SolicitacaoSimplesResponseDTO> excluir(@Valid @PathVariable UUID id) {
    solicitacaoService.delete(id);
    return ResponseEntity.ok(new SolicitacaoSimplesResponseDTO(id));
  }

  /**
   * Endpoint para obter a lista de acompanhamentos vinculados ao ID da solicitação passado como parâmetro.
   *
   * @param id  O UUID da solicitação.
   * @return Uma lista com os dados dos acompanhamentos.
   */
  @Operation(
    summary = "Obter a lista de acompanhamentos vinculados ao ID da solicitação",
    description = "Obtém a lista de acompanhamentos vinculados ao ID da solicitação passado como parâmetro."
  )
  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "Lista de acompanhamentos encontrada",
        content = @Content(schema = @Schema(implementation = SolicitacaoResponseDTO.class))
      ),
      @ApiResponse(responseCode = "404", description = "Solicitação não encontrada"),
      @ApiResponse(responseCode = "422", description = "Um ou mais campos são inválidos"),
    }
  )
  @GetMapping("/solicitacao/{id}/acompanhamentos")
  public ResponseEntity<?> listarAcompanhamentos(
    @Valid @PathVariable UUID id,
    @PageableDefault(size = 20, sort = "dataCriacao", direction = Sort.Direction.DESC) Pageable pageable
  ) {
    return ResponseEntity.status(HttpStatus.OK).body(this.solicitacaoService.listarAcompanhamentosPorSolicitacaoId(id, pageable));
  }

  /**
   * Endpoint para obter a lista de atualizações vinculadas ao ID da solicitação passado como parâmetro.
   *
   * @param id  O UUID da solicitação.
   * @return Uma lista com os dados das atualizações.
   */
  @Operation(
    summary = "Obter a lista de atualizações vinculadas ao ID da solicitação",
    description = "Obtém a lista de atualizações vinculadas ao ID da solicitação passado como parâmetro."
  )
  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "Lista de atualizações encontrada",
        content = @Content(schema = @Schema(implementation = AtualizacaoResponseDTO.class))
      ),
      @ApiResponse(responseCode = "404", description = "Solicitação não encontrada"),
      @ApiResponse(responseCode = "422", description = "Um ou mais campos são inválidos"),
    }
  )
  @GetMapping("/solicitacao/{id}/atualizacoes")
  public ResponseEntity<?> listarAtualizacoes(
    @Valid @PathVariable UUID id,
    @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
  ) {
    return ResponseEntity.status(HttpStatus.OK).body(this.solicitacaoService.listarAtualizacoesPorSolicitacaoId(id, pageable));
  }


    @Operation(summary = "Realizar Análise (Aprovar/Rejeitar)", 
                description = "Atualiza o status da solicitação. Se Rejeitado, exige justificativa.")
      @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Análise processada com sucesso"),
          @ApiResponse(responseCode = "400", description = "Justificativa ausente para rejeição ou status inválido"),
          @ApiResponse(responseCode = "404", description = "Solicitação não encontrada")
      })
      @PutMapping("/solicitacao/analises/analise/status")
      public ResponseEntity<?> processarAnalise(@RequestBody @Valid SolicitacaoAnaliseRequestDTO dto) {
        var response = requerimentoAlvaraConstrucaoService.processarAnalise(dto);
        return ResponseEntity.ok(response);
      }

      
  /**
   * Endpoint para recuperar uma lista paginada de solicitações filtradas por tipo OU status.
   * Ambos os filtros (tipo e status) são opcionais e fornecidos como parâmetros de consulta.
   *
   * @param tipo O tipo de solicitação para filtrar (opcional).
   * @param status O status (novoStatus) para filtrar solicitações (opcional).
   * @param pageable Informações de paginação e ordenação.
   * @return Uma página de SolicitacaoResponseDTO (Status 200 OK).
   * @author Jorge Roberto / Caio Alves
   */
  @Operation(
    summary = "Listar Solicitações por Filtro (Tipo ou Status)",
    description = "Recupera uma lista paginada de solicitações de serviço filtradas por um tipo específico ou pelo status atual registrado no Histórico de Status. Os filtros são opcionais."
  )
  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "Lista paginada de solicitações recuperada com sucesso",
        content = @Content(array = @ArraySchema(schema = @Schema(implementation = SolicitacaoResponseDTO.class)))
      ),
      @ApiResponse(responseCode = "404", description = "Nenhuma solicitação encontrada correspondente aos critérios."),
    }
  )
  @GetMapping(path = "/", params = "status")
  public ResponseEntity<?> listarSolicitacoesPorFiltro(
    @RequestParam(required = false, name = "status") @Schema(
      description = "O status (novoStatus) para filtrar as solicitações."
    ) String status,
    @RequestParam(required = false) String tipo,
    @ParameterObject @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
  ) {
    if (status != null && !status.isEmpty()) {
      Page<SolicitacaoResponseDTO> solicitacoes = solicitacaoService.listarPorNovoStatusHistorico(status, pageable);
      return ResponseEntity.ok(solicitacoes);
    }

    if (tipo != null && !tipo.isEmpty()) {
      Page<SolicitacaoResponseDTO> solicitacoes = solicitacaoService.buscarPorTipo(tipo, pageable);
      return ResponseEntity.ok(solicitacoes);
    }

    return ResponseEntity.ok(Page.empty());
  }

  /**
   * Endpoint para recuperar uma lista paginada de solicitações que foram concluídas ou rejeitadas.
   *
   * @param pageable Informações de paginação e ordenação.
   * @return Uma página de SolicitacaoDetalhadaResponseDTO (Status 200 OK).
   */
  @Operation(
    summary = "Listar Solicitações Finalizadas (Concluídas ou Rejeitadas)",
    description = "Recupera uma lista paginada de solicitações cujo Histórico de Status indica um status final (CONCLUIDO ou REJEITADO)."
  )
  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "Lista paginada de solicitações finalizadas recuperada com sucesso",
        content = @Content(array = @ArraySchema(schema = @Schema(implementation = SolicitacaoResponseDTO.class)))
      ),
      @ApiResponse(responseCode = "404", description = "Nenhuma solicitação finalizada encontrada."),
    }
  )
  @GetMapping("/finalizadas") 
  public ResponseEntity<Page<SolicitacaoDetalhadaResponseDTO>> listarFinalizadas(
      @ParameterObject SolicitacaoFinalizadaRequestDTO filtro, 
      @ParameterObject Pageable pageable
  ) {
      return ResponseEntity.ok(solicitacaoService.listarSolicitacoesFinalizadas(filtro, pageable));
  }


  
/**
   * Endpoint unificado para listar documentos de uma solicitação, com filtro opcional por status.
   *
   * @param id O ID da solicitação (Path Variable).
   * @param status O status para filtrar (Query Param opcional).
   * @return Lista de documentos.
   */
  @Operation(
    summary = "Listar Documentos da Solicitação",
    description = "Recupera os documentos da solicitação. Use o parâmetro 'status' para filtrar (ex: APROVADO OU REJEITADO)."
  )
  @ApiResponses(
    value = {
      @ApiResponse(responseCode = "200", description = "Lista recuperada com sucesso"),
      @ApiResponse(responseCode = "404", description = "Solicitação não encontrada"),
    }
  )
  @GetMapping(value = "/solicitacao/{id}/documentos/status", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<DocumentWithStatusResponseDTO>> listarDocumentos(
      @PathVariable UUID id,
      @RequestParam(required = false) DocumentStatus status 
  ) {
      
    List<DocumentWithStatusResponseDTO> documentos = solicitacaoService.listarDocumentosDaSolicitacaoPorStatus(id, status);
    
    return ResponseEntity.ok(documentos);
  }

  /**
   * Endpoint para retornar TODOS OS DOCUMENTOS associados a uma solicitação.
   *
   * @author Andesson Reis
   */
  @Operation(
    summary = "Listar TODOS os Documentos por ID da Solicitação",
    description = "Retorna uma lista de TODOS os documentos, independentemente do status, associados a um dado 'id' de solicitação."
  )
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Documentos encontrados."),
    @ApiResponse(responseCode = "404", description = "Solicitação ou Requerimento não encontrado.")
  })
  @GetMapping(value = "/solicitacao/{id}/documentos", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<DocumentWithStatusResponseDTO>> listarTodosDocumentosPorSolicitacaoId(
    @PathVariable UUID id
  ) {
    List<DocumentWithStatusResponseDTO> documentos = solicitacaoService.listarTodosDocumentosPorSolicitacaoId(id);
    return ResponseEntity.ok(documentos);
  }

}