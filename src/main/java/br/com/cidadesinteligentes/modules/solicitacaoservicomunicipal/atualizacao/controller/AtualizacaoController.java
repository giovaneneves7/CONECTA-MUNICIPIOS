package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.atualizacao.controller;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.atualizacao.dto.request.AtualizacaoRequestDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.atualizacao.dto.response.AtualizacaoResponseDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.atualizacao.service.IAtualizacaoService;
import br.com.cidadesinteligentes.infraestructure.util.ResultError;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

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
 * Controller responsável por manipular os endpoints de Atualização.
 *
 * @author Giovane Neves
 */
@RestController
@RequestMapping(path = "/api/v1/atualizacoes")
@RequiredArgsConstructor
public class AtualizacaoController {

    private final IAtualizacaoService atualizacaoService;

    @Operation(summary = "Listar todas as atualizações com paginação",
            description = "Recupera uma lista paginada de atualizações.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista paginada de atualizações",
                    content = @Content(schema = @Schema(implementation = AtualizacaoResponseDTO.class)))
    })
    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> listarTodas(@PageableDefault(size = 10, sort = "dataHora", direction = Sort.Direction.ASC) Pageable pageable) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(this.atualizacaoService.findAll(pageable));

    }

    @Operation(summary = "Criar uma nova Atualização",
       description = "Cria e persiste uma nova atualização no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atualização criada com sucesso",
                    content = @Content(schema = @Schema(implementation = AtualizacaoRequestDTO.class))),
            @ApiResponse(responseCode = "400", description = "Corpo da requisição inválido"),
            @ApiResponse(responseCode = "422", description = "Um ou mais campos são inválidos")
    })
    @PostMapping("/atualizacao")
    public ResponseEntity<?> salvar(@RequestBody AtualizacaoRequestDTO dto, BindingResult result){

        return result.hasErrors()
                ? ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResultError.getResultErrors(result))
                : ResponseEntity.ok(this.atualizacaoService.save(dto));

    }

}