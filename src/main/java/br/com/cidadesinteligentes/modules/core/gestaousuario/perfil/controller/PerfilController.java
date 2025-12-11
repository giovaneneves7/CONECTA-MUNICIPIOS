package br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.controller;

import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.dto.request.PerfilAlterarTipoAtivoRequestDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servicomunicipal.dto.response.MunicipalServiceResponseDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.permissao.dto.response.PermissaoResponseDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.dto.request.PerfilAtualizarRequestDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.dto.response.PerfilVerificarTipoAtivoResponseDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.dto.response.PerfilApenasIdResponseDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.service.IPerfilService;
import br.com.cidadesinteligentes.infraestructure.util.ObjectMapperUtil;
import br.com.cidadesinteligentes.infraestructure.util.ResultError;
import br.com.cidadesinteligentes.infraestructure.util.dto.PageableDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * REST Controller responsável por gerenciar perfil
 */
@RestController
@RequestMapping("/api/v1/perfis")
@RequiredArgsConstructor
public class PerfilController {
    private final IPerfilService profileService;
    private final ObjectMapperUtil objectMapperUtil;

    /**
     * Atualiza o tipo e a URL da imagem de um perfil existente.
     *
     * @param body O DTO contendo o ID do perfil e as novas informações (tipo e imagemUrl).
     * @param result Resultado da validação do DTO.
     * @return ResponseEntity com o DTO do perfil atualizado e status 200 OK.
     */
    @Operation(summary = "Atualiza um perfil existente",
            description = "Atualiza um perfil, com as novas informações enviadas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perfil atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Conteúdo de requisição inválido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Perfil não encontrado."),
            @ApiResponse(responseCode = "422", description = "Um ou mais campos são inválidos", content = @Content)
    })
    @PutMapping(path = "/perfil", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestBody @Valid PerfilAtualizarRequestDTO body, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ResultError.getResultErrors(result));
        }

        return ResponseEntity.ok(this.profileService.update(body));
    }

    /**
     * Exclui um perfil existente com base no seu ID.
     *
     * @param id O UUID do perfil a ser excluído.
     * @return ResponseEntity com o DTO contendo o ID do perfil excluído e status 200 OK.
     */
    @Operation(summary = "Apaga um perfil existente",
            description = "Apaga um perfil existente, usando o ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Perfil foi removido/deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Perfil não encontrado"),
            @ApiResponse(responseCode = "409", description = "Esse perfil está relacionado a alguma classe, por isso não pode ser excluído no momento."),
    })
    @DeleteMapping(value = "/perfil/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PerfilApenasIdResponseDTO> delete(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(profileService.delete(id));
    }

    /**
     * Lista todos os perfis do sistema com suporte à paginação.
     *
     * @param pageable Parâmetros de paginação e ordenação (padrão: 10 itens, ordenado por 'id' ascendente).
     * @return ResponseEntity contendo a lista paginada de perfis com status 200 OK.
     */
    @Operation(summary = "Lista todos os perfis com paginação ",
            description = "Retorna a paginação de uma lista de perfis")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de perfis paginada",
                    content = @Content(schema = @Schema(implementation = PageableDTO.class)))
    })
    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAll(@ParameterObject @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(this.profileService.findAll(pageable));

    }


    /**
     * Busca um perfil específico pelo seu ID e retorna dados públicos e de usuário.
     *
     * @param profileId O UUID do perfil a ser buscado.
     * @return ResponseEntity contendo o DTO de dados públicos do perfil e status 200 OK.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado", content = @Content(schema = @Schema(implementation = MunicipalServiceResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping(path = "/perfil/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUsuarioId(@Valid @PathVariable("id") final UUID profileId){

        return ResponseEntity.status(HttpStatus.OK).body(this.profileService.findById(profileId));

    }

    /**
     * Retorna uma lista paginada de todas as solicitações de serviço atreladas a um perfil.
     *
     * @param profileId O UUID do perfil para o qual as solicitações serão buscadas.
     * @param pageable Parâmetros de paginação e ordenação.
     * @return ResponseEntity contendo uma Page de DTOs de solicitações com status 200 OK.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de Solicitações foi encontrada", content = @Content(schema = @Schema(implementation = MunicipalServiceResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Lista de Solicitações foi encontrada")
    })
    @GetMapping(path = "/perfil/{id}/requests", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<?>> getRequestListByUsuarioId(@Valid @PathVariable("id") final UUID profileId, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){

        return ResponseEntity.status(HttpStatus.OK).body(this.profileService.findAllRequestsByPerfilId(profileId, pageable));

    }

    /**
     * Altera o perfil ativo do usuário autenticado para um novo perfil existente.
     *
     * @param dto O DTO contendo o ID do usuário e o tipo do novo perfil que deve se tornar ativo.
     * @param result Resultado da validação do DTO.
     * @return ResponseEntity com o DTO de verificação do novo perfil ativo e status 201 Created.
     */
    @Operation(summary = "Altera/Troca o perfil ativo do usuário",
            description = "Altera/Troca o perfil ativo do usuário autenticado, por um novo perfil")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de perfil alterado com sucesso",
                    content = @Content(schema = @Schema(implementation = PerfilVerificarTipoAtivoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Tipo de perfil fornecido é inválido",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "O usuário ou perfil não foram encontrados",
                    content = @Content),
            @ApiResponse(responseCode = "422", description = "Um ou mais campos estão incorretos.",
                    content = @Content)
    })
    @PatchMapping(path = "/perfil", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateTipoAtivoPerfil(
            @RequestBody @Valid PerfilAlterarTipoAtivoRequestDTO dto,
            BindingResult result
    ) {
        return result.hasErrors()
                ? ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ResultError.getResultErrors(result))
                : ResponseEntity.status(HttpStatus.CREATED).body(this.profileService.changeActivePerfil(dto.usuarioId(), dto.tipoAtivo()));
    }

    /**
     * Retorna todas as permissões associadas ao cargo de um perfil específico.
     *
     * @param perfilId O UUID do perfil para buscar as permissões.
     * @param pageable Parâmetros de paginação e ordenação (embora a paginação possa ser ignorada se a lista for pequena).
     * @return ResponseEntity contendo a lista de permissões e status 200 OK.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Permissões encontradas para o perfil"),
            @ApiResponse(responseCode = "404", description = "Perfil não encontrado")
    })
    @GetMapping(path = "/perfil/{perfilId}/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAllPermissoesByPerfil (
            @PathVariable("perfilId") UUID perfilId,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(this.profileService.findAllPermissoesByPerfil(perfilId, pageable));
    }
}
