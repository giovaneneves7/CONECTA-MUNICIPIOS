package br.com.cidadesinteligentes.modules.gestaomanutencaourbana.gestor.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GestorResponseDTO {

    private UUID id;
    private String imagemUrl;
    private String tipo; // Para retornar "GESTOR_MANUTENCAO"
}