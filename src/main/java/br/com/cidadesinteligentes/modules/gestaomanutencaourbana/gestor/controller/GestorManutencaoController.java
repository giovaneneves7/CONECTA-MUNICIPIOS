package br.com.cidadesinteligentes.modules.gestaomanutencaourbana.gestor.controller;

import br.com.cidadesinteligentes.infraestructure.util.ResultError;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.gestor.dto.request.GestorRequestDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.gestor.dto.response.GestorResponseDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.gestor.service.IGestorManutencaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/manutencao-urbana/gestores")
@Tag(name = "Gestores de Manutenção", description = "Endpoints para gerenciamento de perfis de gestores")
@RequiredArgsConstructor
public class GestorManutencaoController {

    private final IGestorManutencaoService service;

    @Operation(summary = "Criar novo Gestor", description = "Cria um perfil de Gestor de Manutenção Urbana.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Gestor criado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Erro de validação nos dados")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createGestor(@RequestBody @Valid GestorRequestDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(ResultError.getResultErrors(result));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createGestor(dto));
    }

    @Operation(summary = "Listar Gestores", description = "Retorna todos os gestores cadastrados.")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GestorResponseDTO>> findAllGestores() {
        return ResponseEntity.ok(service.findAllGestores());
    }

    @Operation(summary = "Buscar Gestor por ID")
    @GetMapping("/{id}")
    public ResponseEntity<GestorResponseDTO> findGestorById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findGestorById(id));
    }

    @Operation(summary = "Atualizar Gestor")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateGestor(@PathVariable UUID id,
                                          @RequestBody @Valid GestorRequestDTO dto,
                                          BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(ResultError.getResultErrors(result));
        }
        return ResponseEntity.ok(service.updateGestor(id, dto));
    }

    @Operation(summary = "Deletar Gestor")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGestor(@PathVariable UUID id) {
        service.deleteGestor(id);
        return ResponseEntity.noContent().build();
    }
}