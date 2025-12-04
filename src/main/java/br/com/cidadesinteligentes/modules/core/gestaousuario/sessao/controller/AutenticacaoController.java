package br.com.cidadesinteligentes.modules.core.gestaousuario.sessao.controller;

import br.com.cidadesinteligentes.infraestructure.util.ResultError;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.dto.request.UsuarioLoginRequestDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.dto.request.UsuarioCadastrarRequestDTO;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.dto.response.UsuarioLoginResponseDTO;
import br.com.cidadesinteligentes.infraestructure.service.AutenticacaoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller responsável pelos endpoints de autenticação
 */
@RestController
@RequestMapping("/api/v1/autenticacao")
@RequiredArgsConstructor
public class AutenticacaoController {

    private final AutenticacaoService autenticacaoService;

    /**
     * Endpoint para autenticar usuário com email e senha
     *
     * @param body DTO que contem email e senha.
     * @return Retornar informações do usuário autenticado ou erro.
     */
    @Operation(summary = "Login de usuário",
            description = "Autentica um usuário usando email e senha, retornando um token JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autenticação bem-sucedida, retorna token JWT",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UsuarioLoginResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas (email ou senha incorretos)",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ResultError.class))),
            @ApiResponse(responseCode = "400", description = "Corpo da requisição inválido",
                    content = @Content),
            @ApiResponse(responseCode = "422", description = "Validação de campos falhou",
                    content = @Content)
    })
    @PostMapping("/sessoes/sessao")
    public ResponseEntity<?> login(@RequestBody @Valid UsuarioLoginRequestDTO body) {
        try {
            UsuarioLoginResponseDTO response = autenticacaoService.login(body);
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
    }

    /**
     * Endpoint para cadastrar novo usuário
     *
     * @param body DTO contendo informações de registro de usuário.
     * @return Retorna dados de usuário cadastrado ou erro.
     */
    @Operation(summary = "Cadastro de novo usuário",
            description = "Cria um novo usuário (Cidadão) no sistema, " +
                    "atribuindo o perfil inicial e retornando um token JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário registrado com sucesso",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UsuarioLoginResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Corpo da requisição inválido",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflito: Email ou CPF já existem",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ResultError.class)))
    })
    @PostMapping("/usuarios/usuario")
    public ResponseEntity<?> registrarUsuario(@RequestBody @Valid UsuarioCadastrarRequestDTO body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(autenticacaoService.register(body));
    }
}
