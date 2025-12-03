package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servicomunicipal.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.service.IFluxoService;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servicomunicipal.dto.request.MunicipalServiceRequestDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servicomunicipal.dto.response.MunicipalServiceResponseDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servicomunicipal.dto.response.MunicipalServiceSimpleResponseDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servicomunicipal.service.IServicoMunicipalService;
import br.com.cidadesinteligentes.infraestructure.util.ResultError;


/**
 * Controller responsável pelos endpoints de Serviço Municipal.
 * Permite criar, listar, buscar e excluir serviços municipais.
 *
 * @author: Caio Alves, Jorge Roberto, Andesson Reis
 */
@RestController
@RequestMapping("/api/v1/servicos-municipais")
@RequiredArgsConstructor
public class MunicipalServiceController {

    private final IServicoMunicipalService servicoMunicipalService;
    private final IFluxoService fluxoService;

    /**
     * Endpoint para criar um novo serviço municipal.
     *
     * @param dto dados enviados para criação do serviço
     * @return serviço criado
     */
    @Operation(
            summary = "Criar um novo Serviço Municipal",
            description = "Cria e persiste um novo serviço municipal no sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Serviço municipal criado com sucesso",
                    content = @Content(schema = @Schema(implementation = MunicipalServiceResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "422", description = "Um ou mais campos são inválidos")
    })
    @PostMapping(
            path = "/servico-municipal",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> criar(@RequestBody MunicipalServiceRequestDTO dto, BindingResult result) {

        return result.hasErrors()
                ? ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResultError.getResultErrors(result))
                : ResponseEntity.ok(servicoMunicipalService.save(dto));
    }

    /**
     * Endpoint para listar todos os serviços municipais.
     *
     * @return lista de serviços
     */
    @Operation(
            summary = "Listar todos os Serviços Municipais",
            description = "Retorna a lista de todos os serviços municipais cadastrados."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtida com sucesso",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = MunicipalServiceResponseDTO.class))))
    })
    @GetMapping
    public ResponseEntity<List<MunicipalServiceResponseDTO>> listarTodos(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return ResponseEntity.ok(servicoMunicipalService.findAll(pageable));
    }

    /**
     * Endpoint para buscar um serviço municipal pelo ID.
     *
     * @param id identificador
     * @return serviço encontrado
     */
    @Operation(
            summary = "Buscar Serviço Municipal por ID",
            description = "Retorna os detalhes de um serviço municipal pelo seu ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Serviço encontrado",
                    content = @Content(schema = @Schema(implementation = MunicipalServiceResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Serviço não encontrado")
    })
    @GetMapping(path = "/servico-municipal/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MunicipalServiceResponseDTO> buscarPorId(@Valid @PathVariable Long id) {
        return ResponseEntity.ok(servicoMunicipalService.findById(id));
    }

    /**
     * Endpoint para excluir um serviço municipal.
     *
     * @param id identificador
     * @return dados do serviço excluído
     */
    @Operation(
            summary = "Excluir Serviço Municipal por ID",
            description = "Remove um serviço municipal do sistema pelo seu ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Serviço excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Serviço não encontrado")
    })
    @DeleteMapping(path = "/servico-municipal/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MunicipalServiceSimpleResponseDTO> excluir(@Valid @PathVariable Long id) {
        servicoMunicipalService.delete(id);
        return ResponseEntity.ok(new MunicipalServiceSimpleResponseDTO(id));
    }

    /**
     * Endpoint para obter o fluxo associado a um serviço municipal.
     *
     * @param id ID do serviço municipal
     * @return fluxo encontrado
     */
    @Operation(
            summary = "Obter fluxo do Serviço Municipal",
            description = "Retorna o fluxo de trabalho associado ao serviço municipal pelo ID informado."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fluxo encontrado"),
            @ApiResponse(responseCode = "404", description = "Fluxo não encontrado")
    })
    @GetMapping(
            path = "/servico-municipal/{id}/fluxos/fluxo",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> obterFluxoPorServico(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.fluxoService.buscarFluxoPorServicoMunicipalId(id));
    }
}
