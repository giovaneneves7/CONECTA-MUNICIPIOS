package br.com.cidadesinteligentes.modules.core.gestaousuario.cargo.controller;

import br.com.cidadesinteligentes.modules.core.gestaousuario.permissao.dto.request.PermissaoRequestDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.cargo.service.ICargoService;
import br.com.cidadesinteligentes.infraestructure.util.ResultError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller responsável por gerenciar Cargo.
 */
@RestController
@RequestMapping("/api/v1/cargos")
@RequiredArgsConstructor
public class CargoController {
    private final ICargoService cargoService;

    /**
     * Adiciona uma permissão a um cargo específico.
     * * A Função recebe o ID do cargo na URL e o nome da permissão no corpo da requisição.
     * A operação falha se a permissão já estiver presente no cargo (409 Conflict).
     *
     * @param cargoId O ID do cargo ao qual a permissão será adicionada. Deve ser um ID válido.
     * @param dto O DTO contendo o nome da permissão a ser adicionada.
     * @param result O resultado da validação do DTO.
     * @return ResponseEntity com o DTO da permissão adicionada e status 200 OK, ou 4xx em caso de erro.
     */
    @Operation(summary = "Adiciona uma permissão a um cargo de um perfil",
            description = "Adiciona uma das permissões, se ela existir no banco de dados ao cargo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Permissão adicionada com sucesso ao cargo"),
            @ApiResponse(responseCode = "400", description = "Conteúdo da requisição é inválido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Permissão ou cargo não foram encontrados"),
            @ApiResponse(responseCode = "409", description = "Esse cargo já possui essa permissão"),
            @ApiResponse(responseCode = "422", description = "Um ou mais campos são inválidos", content = @Content)
    })
    @PatchMapping("/cargo/{cargoId}/permissoes")
    public ResponseEntity<?> addPermission(
            @PathVariable("cargoId") @NotNull Long cargoId,
            @RequestBody @Valid PermissaoRequestDTO dto,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ResultError.getResultErrors(result));
        }
        return ResponseEntity.ok(cargoService.adicionarPermissao(dto.nome(), cargoId));
    }

    /**
     * Remove uma permissão de um cargo específico.
     * * A Função recebe o ID do cargo na URL e o nome da permissão no corpo da requisição.
     * A operação falha se a permissão não for encontrada no cargo (404 Not Found).
     *
     * @param cargoId O ID do cargo do qual a permissão será removida. Deve ser um ID válido.
     * @param dto O DTO contendo o nome da permissão a ser removida.
     * @param result O resultado da validação do DTO.
     * @return ResponseEntity com o DTO da permissão removida e status 200 OK, ou 4xx em caso de erro.
     */
    @Operation(summary = "Remove a permissão de um cargo",
            description = "Remove umas das permissões de um cargo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Permissão removida com sucesso"),
            @ApiResponse(responseCode = "400", description = "Conteúdo da requisição é inválido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Permissão ou cargo não foram encontrados"),
            @ApiResponse(responseCode = "422", description = "Um ou mais campos são inválidos", content = @Content)
    })
    @DeleteMapping("/cargo/{cargoId}/permissoes")
    public ResponseEntity<?> removePermission(
            @PathVariable("cargoId") @NotNull Long cargoId,
            @RequestBody @Valid PermissaoRequestDTO dto,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ResultError.getResultErrors(result));
        }
        return ResponseEntity.ok(cargoService.removerPermissao(dto.nome(), cargoId));
    }
}
