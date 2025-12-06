package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.itemavaliacaogeral.controller;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.itemavaliacaogeral.dto.request.ItemAvaliacaoGeralRequestDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.itemavaliacaogeral.dto.request.ItemAvaliacaoGeralAtualizacaoRequestDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.itemavaliacaogeral.dto.response.ItemAvaliacaoGeralResponseDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.itemavaliacaogeral.model.ItemAvaliacaoGeral;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.itemavaliacaogeral.service.IItemAvaliacaoGeralService;
import br.com.cidadesinteligentes.infraestructure.util.ObjectMapperUtil;
import br.com.cidadesinteligentes.infraestructure.util.ResultError;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Controller REST responsável pelo gerenciamento dos recursos de Item de Avaliação Geral ({@link ItemAvaliacaoGeral}).
 *
 * @author Andesson Reis
 */
@RestController
@RequestMapping("/api/v1/itens-avaliacao-geral")
@RequiredArgsConstructor
public class ItemAvaliacaoGeralController {

    private final IItemAvaliacaoGeralService itemAvaliacaoGeralService;
    private final ObjectMapperUtil objectMapperUtil;

    /**
     * Cria e persiste um novo Item de Avaliação Geral associado a uma Solicitação existente.
     *
     * @param dto O DTO contendo os dados do item e o ID da solicitação alvo.
     * @param result Resultado da validação do DTO.
     * @return ResponseEntity com o DTO do item criado (Status 200 OK).
     */
    @Operation(summary = "Criar Item de Avaliação Geral",
            description = "Cria um novo item de avaliação e o associa a uma Solicitação existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item criado com sucesso",
                    content = @Content(schema = @Schema(implementation = ItemAvaliacaoGeralResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Solicitação alvo não encontrada."),
            @ApiResponse(responseCode = "422", description = "Campos inválidos fornecidos",
                    content = @Content(schema = @Schema(implementation = ResultError.class)))
    })
    @PostMapping(value = "/item-avaliacao-geral", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> salvar(
        @Valid @RequestBody ItemAvaliacaoGeralRequestDTO dto,
        BindingResult result
    ) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ResultError.getResultErrors(result));
        }
        ItemAvaliacaoGeralResponseDTO response = this.itemAvaliacaoGeralService.save(
                objectMapperUtil.map(dto, ItemAvaliacaoGeral.class),
                dto.solicitacaoId()
        );
        return ResponseEntity.ok(response);
    }

    /**
     * Recupera todos os Itens de Avaliação Geral associados a um ID de Solicitação específico.
     *
     * @param solicitacaoId O ID da Solicitação cujos itens de avaliação devem ser buscados.
     * @param pageable Parâmetros de paginação e ordenação (padrão por 'id' ascendente).
     * @return ResponseEntity contendo a lista paginada de itens (Status 200 OK).
     */
    @Operation(summary = "Listar Itens de Avaliação por ID da Solicitação",
            description = "Retorna uma lista paginada de todos os itens de avaliação vinculados ao ID da Solicitação fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de itens recuperada com sucesso",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ItemAvaliacaoGeralResponseDTO.class)))),
            @ApiResponse(responseCode = "404", description = "Solicitação não encontrada.")
    })
    @GetMapping(path = "/item-avaliacao-geral/solicitacao/{solicitacaoId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> listarPorSolicitacaoId(
            @PathVariable("solicitacaoId") UUID solicitacaoId,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(this.itemAvaliacaoGeralService.findAllBySolicitacaoId(solicitacaoId, pageable));
    }

    /**
     * Atualiza um Item de Avaliação Geral existente.
     *
     * @param id O ID do item de avaliação a ser atualizado.
     * @param dto O DTO contendo os novos dados (observação e status).
     * @param result Resultado da validação do DTO.
     * @return ResponseEntity vazia (Status 204 No Content) após atualização bem-sucedida.
     */
    @Operation(summary = "Atualizar Item de Avaliação",
            description = "Atualiza a observação e o status de um Item de Avaliação Geral existente pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Item atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Item de Avaliação Geral não encontrado."),
            @ApiResponse(responseCode = "422", description = "Campos inválidos fornecidos",
                    content = @Content(schema = @Schema(implementation = ResultError.class)))
    })
    @PutMapping(value = "/item-avaliacao-geral/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> atualizar(
            @PathVariable("id") Long id,
            @Valid @RequestBody ItemAvaliacaoGeralAtualizacaoRequestDTO dto,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ResultError.getResultErrors(result));
        }

        this.itemAvaliacaoGeralService.update(objectMapperUtil.map(dto, ItemAvaliacaoGeral.class), id);
        return ResponseEntity.noContent().build();
    }
}