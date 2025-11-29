package br.com.cidadesinteligentes.modules.gestaomanutencaourbana.endereco.controller;

import br.com.cidadesinteligentes.infraestructure.util.ResultError;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.endereco.dto.request.EnderecoRequestDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.endereco.dto.response.EnderecoResponseDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.endereco.service.IEnderecoService;
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
@RequestMapping("/api/v1/manutencao-urbana/enderecos")
@Tag(name = "Endereços", description = "Gestão de endereços para as manutenções urbanas")
@RequiredArgsConstructor
public class EnderecoController {

    private final IEnderecoService service;

    @Operation(summary = "Cadastrar Endereço", description = "Cria um novo registro de endereço.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Endereço criado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Erro de validação nos dados enviados")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody @Valid EnderecoRequestDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(ResultError.getResultErrors(result));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @Operation(summary = "Listar Endereços", description = "Retorna todos os endereços cadastrados.")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EnderecoResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(summary = "Buscar Endereço por ID")
    @GetMapping("/{id}")
    public ResponseEntity<EnderecoResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "Atualizar Endereço")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @RequestBody @Valid EnderecoRequestDTO dto,
                                    BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(ResultError.getResultErrors(result));
        }
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Deletar Endereço")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}