package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.atividade.controller;

import br.com.cidadesinteligentes.infraestructure.util.ObjectMapperUtil;
import br.com.cidadesinteligentes.infraestructure.util.ResultError;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.atividade.dto.request.AtividadeRequestDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.atividade.dto.response.AtividadeDadosCompletosResponseDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.atividade.model.Atividade;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.atividade.service.IAtividadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador para operações relacionadas a Atividades do Fluxo de Serviço Municipal.
 * @author Giovane Neves, Andesson Reis
 */
@RestController
@RequestMapping("/api/v1/atividades")
@RequiredArgsConstructor
@Tag(name = "Atividades do Fluxo", description = "Gerencia as atividades dentro de um fluxo de solicitação.")
public class AtividadeController {

    private final IAtividadeService atividadeService;
    private final ObjectMapperUtil objectMapperUtil;

    /**
     * Cria uma nova Atividade para o fluxo no sistema.
     * @param dto Os dados da atividade a ser criada.
     * @param result O resultado da validação do DTO.
     * @return ResponseEntity com a atividade criada ou erros de validação.
     */
    @Operation(summary = "Criar nova Atividade para Fluxo",
            description = "Cria e persiste uma nova **atividade** do fluxo no sistema.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Atividade criada com sucesso",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Atividade.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Requisição inválida (Erro na estrutura ou sintaxe)",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "422",
                    description = "Campos da requisição inválidos",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ResultError.class)
                    )
            )
    })
    @PostMapping(path = "/atividade", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE) 
        public ResponseEntity<?> criarAtividade(
            @RequestBody @Valid AtividadeRequestDTO dto,
            BindingResult result
    ) {

        if (result.hasErrors()) {
            return ResponseEntity
                    .status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(ResultError.getResultErrors(result));
        }

        Atividade novaAtividade = this.objectMapperUtil.map(dto, Atividade.class);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.atividadeService.save(novaAtividade));
    }

    /**
     * Retorna todas as Atividades cadastradas, com paginação.
     * @param pageable Parâmetros de paginação e ordenação.
     * @return ResponseEntity com a lista paginada de atividades encontradas.
     */
    @Operation(
            summary = "Obter todas as Atividades ",
            description = "Obtém uma lista paginada de todas as **atividades** cadastradas no banco de dados."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de atividades encontrada com sucesso",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = AtividadeDadosCompletosResponseDTO.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Nenhuma atividade encontrada",
                    content = @Content
            )
    })
    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obterAtividades(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.atividadeService.findAll(pageable));

    }
}