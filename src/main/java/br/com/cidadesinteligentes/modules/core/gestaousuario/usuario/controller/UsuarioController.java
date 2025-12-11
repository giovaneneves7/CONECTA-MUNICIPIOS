package br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.controller;

import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.dto.response.PerfilComCargoResponseDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.dto.response.PerfilVerificarTipoAtivoResponseDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.dto.response.UsuarioCompletoResponseDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.dto.response.UsuarioResponseDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.enums.StatusUsuario;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.service.IUsuarioService;
import br.com.cidadesinteligentes.infraestructure.util.ObjectMapperUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    /**
     * Endpoint para atualizar o status de um usuário.
     *
     * @param usuarioId O ID do usuário a ser atualizado.
     * @param newStatus O novo status do usuário (ex: ATIVO, INATIVO).
     * @return Resposta com status 200 e o usuário atualizado.
     */
    @Operation(summary = "Atualiza o Status do Usuário",
               description = "Altera o status de um usuário específico e retorna os dados atualizados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso.",
                         content = @Content(schema = @Schema(implementation = UsuarioResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado.", content = @Content),
            @ApiResponse(responseCode = "400", description = "Status inválido fornecido.", content = @Content)
    })

    @PutMapping(path = "/usuario/{usuarioId}/status", produces = MediaType.APPLICATION_JSON_VALUE) 
    public ResponseEntity<?> updateStatusUsuario( 
            @PathVariable("usuarioId") UUID usuarioId,
            @RequestParam("newStatus") String newStatus) {
        try {

            StatusUsuario statusEnum = StatusUsuario.valueOf(newStatus.toUpperCase());
            
            UsuarioResponseDTO userUpdated = userService.updateStatusUsuario(usuarioId, statusEnum);
            
            return ResponseEntity.ok(userUpdated); 

        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Status inválido. Valores aceitos: " + java.util.Arrays.toString(StatusUsuario.values()));
        }
    }

    /**
     * Endpoint para listar os detalhes completos de todos os usuários de forma paginada.
     *
     * @param pageable Objeto de paginação (page, size, sort).
     * @return Página com os detalhes completos dos usuários.
     */
    @Operation(summary = "Lista detalhes de todos os usuários",
            description = "Retorna uma lista paginada contendo informações detalhadas (DTO Completo) de todos os usuários.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = UsuarioCompletoResponseDTO.class))))
    })
        @GetMapping(value = "/detalhes", produces = MediaType.APPLICATION_JSON_VALUE)
          public ResponseEntity<Page<UsuarioCompletoResponseDTO>> getAllDetalhesUsuarios(
            @ParameterObject 
            @PageableDefault(size = 10, sort = "pessoa.nomeCompleto", direction = Sort.Direction.ASC)
            Pageable pageable
       ) {
                Page<UsuarioCompletoResponseDTO> userDetailsPage = userService.findAllDetalhesUsuarios(pageable);
        return ResponseEntity.ok(userDetailsPage);
    }

    /**
     * Endpoint para buscar detalhes de usuários filtrando pelo tipo de cargo.
     *
     * @param tipo O nome do cargo/tipo a ser pesquisado.
     * @param pageable Objeto de paginação.
     * @return Página com usuários que possuem o cargo especificado.
     */
    @Operation(summary = "Busca detalhes por Tipo",
            description = "Filtra a lista detalhada de usuários baseado no nome do cargo (tipo).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = UsuarioCompletoResponseDTO.class)))),
            @ApiResponse(responseCode = "400", description = "Parâmetro 'tipo' inválido ou vazio.", content = @Content)
    })
    @GetMapping(value = "tipo", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<UsuarioCompletoResponseDTO>> getDetalhesUsuariosByNomeCargo(
            @RequestParam String tipo, 
            @ParameterObject
            @PageableDefault(size = 10, sort = "pessoa.nomeCompleto", direction = Sort.Direction.ASC)
            Pageable pageable
    ) {
        if (tipo == null || tipo.isBlank()) {
             return ResponseEntity.badRequest().build();
        }
        Page<UsuarioCompletoResponseDTO> userDetailsPage = userService.findDetalhesUsuarioByNomeCargo(tipo, pageable);
        return ResponseEntity.ok(userDetailsPage);
    }

    /**
     * Endpoint para buscar detalhes de usuários filtrando pelo status.
     *
     * @param status O status do usuário (ex: ATIVO, INATIVO).
     * @param pageable Objeto de paginação.
     * @return Página com usuários que possuem o status especificado.
     */
    @Operation(summary = "Busca detalhes por Status",
            description = "Filtra a lista detalhada de usuários baseado no status atual (Enum).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = UsuarioCompletoResponseDTO.class)))),
            @ApiResponse(responseCode = "400", description = "Status fornecido é inválido.", content = @Content)
    })
        @GetMapping(value = "/status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDetalhesUsuariosByStatus(
            @RequestParam String status, 
            @ParameterObject
            @PageableDefault(size = 10, sort = "pessoa.nomeCompleto", direction = Sort.Direction.ASC)
            Pageable pageable
    ) {
        StatusUsuario userStatusEnum;
        try {
            // Convert string for enum
            userStatusEnum = StatusUsuario.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Valor de status inválido. Deve ser um dos seguintes: " + 
                          java.util.Arrays.toString(StatusUsuario.values()));
        }

        Page<UsuarioCompletoResponseDTO> userDetailsPage = userService.findDetalhesUsuariosByStatus(userStatusEnum, pageable);
        return ResponseEntity.ok(userDetailsPage);
    }

    /**
     * Endpoint para buscar usuários por termo (Nome ou CPF).
     *
     * @param termo O termo da busca (pode ser parte do nome ou CPF).
     * @param pageable Objeto de paginação.
     * @return Página com usuários encontrados.
     */
    @Operation(summary = "Busca por Nome ou CPF",
            description = "Realiza uma busca textual nos detalhes do usuário, verificando correspondência no Nome ou no CPF.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = UsuarioCompletoResponseDTO.class)))),
            @ApiResponse(responseCode = "400", description = "Termo de busca vazio.", content = @Content)
    })
    @GetMapping(value = "/usuario", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buscaDetalhesUsuarioPorFiltro(
            @Parameter(description = "Tipo do filtro (valores aceitos: 'cpf' ou 'nome')", example = "cpf", required = true)
            @RequestParam(name = "tipo_filtro") String tipoFiltro,
            
            @Parameter(description = "Valor do filtro a ser pesquisado", example = "12345678900", required = true)
            @RequestParam(name = "valor_filtro") String valorFiltro,
            
            @ParameterObject
            @PageableDefault(size = 10, sort = "pessoa.nomeCompleto", direction = Sort.Direction.ASC)
            Pageable pageable
    ) {
        if (tipoFiltro == null || tipoFiltro.isBlank() || valorFiltro == null || valorFiltro.isBlank()) {
            return ResponseEntity.badRequest().body("Os parâmetros 'tipo_filtro' e 'valor_filtro' são obrigatórios.");
        }

        String filtro = tipoFiltro.trim().toLowerCase();

        if (!filtro.equals("cpf") && !filtro.equals("nome")) {
            return ResponseEntity.badRequest().body("Tipo de filtro inválido. Valores aceitos: 'cpf' ou 'nome'.");
        }
        Page<UsuarioCompletoResponseDTO> userDetailsPage = userService.findDetalhesUsuarioByNomeOuCpf(valorFiltro, pageable);

        return ResponseEntity.ok(userDetailsPage);
    }

    /**
     * Endpoint para buscar usuários combinando filtro de Tipo de Cargo e Status.
     *
     * @param tipo O nome do cargo.
     * @param status O status do usuário.
     * @param pageable Objeto de paginação.
     * @return Página com usuários que atendem a ambos os critérios.
     */
    @Operation(summary = "Busca combinada (Tipo e Status)",
            description = "Filtra usuários que possuam determinado cargo E determinado status simultaneamente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = UsuarioCompletoResponseDTO.class)))),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos (tipo vazio ou status inexistente).", content = @Content)
    })
    @GetMapping(value = "/tipo-e-status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDetalhesUsuariosByNomeCargoAndStatus(
            @RequestParam String tipo, 
            @RequestParam String status,  
            @ParameterObject
            @PageableDefault(size = 10, sort = "pessoa.nomeCompleto", direction = Sort.Direction.ASC)
            Pageable pageable
    ) {
        if (tipo == null || tipo.isBlank()) {
            return ResponseEntity.badRequest().body("O parâmetro 'nomeCargo' não pode estar vazio.");
        }
        StatusUsuario userStatusEnum;
        try {
            userStatusEnum = StatusUsuario.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Valor de status inválido. Deve ser um dos seguintes: " + 
                          java.util.Arrays.toString(StatusUsuario.values()));
        }
        Page<UsuarioCompletoResponseDTO> userDetailsPage = userService.findDetalhesUsuarioByNomeCargoEStatus(tipo, userStatusEnum, pageable);
        return ResponseEntity.ok(userDetailsPage);
    }
}
