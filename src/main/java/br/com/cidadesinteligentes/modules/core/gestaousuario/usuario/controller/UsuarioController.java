package br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.controller;

import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.dto.response.PerfilComCargoResponseDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.dto.response.PerfilVerificarTipoAtivoResponseDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.dto.response.UsuarioResponseDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.service.IUsuarioService;
import br.com.cidadesinteligentes.infraestructure.util.ObjectMapperUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final IUsuarioService userService;
    private final ObjectMapperUtil objectMapperUtil;

    /**
     * Endpoint para encontrar um usuário pelo ID dele.
     *
     * @param id O id do usuário que será procurado
     * @return Resposta com os dados de usuário
     */
    @Operation(summary = "Busca usuário por ID",
            description = "Retorna os dados de um usuário específico, identificado pelo seu UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UsuarioResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado.", content = @Content)
    })
    @GetMapping(path = "/usuario/{id}", produces = "application/json")
    public ResponseEntity<?> getUsuarioById(@PathVariable("id") @NotNull UUID id){

        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.findById(id));

    }

    /**
     * Endpoint para pegar os perfis do usuário, através do ID dele
     *
     * @author Giovane Neves
     * @param id O Id do usuário
     * @return Retorna os dados do perfil do usuário
     */
    @Operation(summary = "Lista perfis do usuário",
            description = "Retorna todos os perfis (cargos) que um determinado usuário possui.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perfis retornados com sucesso.",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = PerfilComCargoResponseDTO.class)))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado.", content = @Content)
    })
    @GetMapping(path = "/usuario/{id}/perfis", produces = "application/json")
    public ResponseEntity<?> getUserProfiles(@PathVariable("id") @NotNull UUID id, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){

        return ResponseEntity.status((HttpStatus.OK))
                .body(userService.getUsuarioPerfis(id, pageable));

    }

    @Operation(summary = "Busca perfil ativo",
            description = "Retorna os dados do perfil que está atualmente ativo para o usuário.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perfil ativo retornado com sucesso.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PerfilVerificarTipoAtivoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado.", content = @Content),
            @ApiResponse(responseCode = "400", description = "Usuário não possui perfis definidos.", content = @Content)
    })
    @GetMapping(path = "/usuario/{id}/perfis/perfil/ativo", produces = "application/json")
    public ResponseEntity<?> getUsuarioPerfilAtivo(@PathVariable("id") UUID userId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.userService.findPerfilAtivoByUsuarioId(userId));
    }

    @Operation(summary = "Lista todos os usuários",
            description = "Retorna a lista paginada de todos os usuários cadastrados no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuários retornada.",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = UsuarioResponseDTO.class))))
    })
    @GetMapping
    public ResponseEntity<?> findAllUsers(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(this.userService.findAll(pageable));
    }
}
