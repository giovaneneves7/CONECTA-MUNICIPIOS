package br.com.cidadesinteligentes.modules.gestaomanutencaourbana.manutencaourbana.controller;

import br.com.cidadesinteligentes.infraestructure.util.ResultError;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.manutencaourbana.dto.request.ManutencaoUrbanaRequestDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.manutencaourbana.dto.response.ManutencaoUrbanaResponseDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.manutencaourbana.service.IManutencaoUrbanaService;
import io.swagger.v3.oas.annotations.Operation;
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

@RestController
@RequestMapping("/api/v1/manutencao-urbana/solicitacoes")
@Tag(name = "Solicitações de Manutenção", description = "Gerencia as solicitações de manutenção urbana")
@RequiredArgsConstructor
public class ManutencaoUrbanaController {

    private final IManutencaoUrbanaService service;

    @Operation(summary = "Criar nova solicitação", description = "Registra uma nova manutenção urbana.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Criado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Erro de validação")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody @Valid ManutencaoUrbanaRequestDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(ResultError.getResultErrors(result));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @Operation(summary = "Listar todas")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ManutencaoUrbanaResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(summary = "Buscar por ID")
    @GetMapping("/{id}")
    public ResponseEntity<ManutencaoUrbanaResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "Atualizar solicitação")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @RequestBody @Valid ManutencaoUrbanaRequestDTO dto,
                                    BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(ResultError.getResultErrors(result));
        }
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Deletar solicitação")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}