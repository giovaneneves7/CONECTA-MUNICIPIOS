package br.com.cidadesinteligentes.modules.gestaomanutencaourbana.gestor.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GestorRequestDTO {

    @NotBlank(message = "A URL da imagem é obrigatória")
    private String imagemUrl;
}