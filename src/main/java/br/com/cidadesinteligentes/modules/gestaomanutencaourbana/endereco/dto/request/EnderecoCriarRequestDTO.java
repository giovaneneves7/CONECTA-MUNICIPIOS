package br.com.cidadesinteligentes.modules.gestaomanutencaourbana.endereco.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EnderecoCriarRequestDTO(
        @JsonProperty("rua")
        @NotBlank(message = "A rua é obrigatória")
        @Size(max = 150, message = "A rua deve ter no máximo 150 caracteres")
        String rua,

        @JsonProperty("numero")
        @NotBlank(message = "O número é obrigatório")
        @Size(max = 20, message = "O número deve ter no máximo 20 caracteres")
        String numero,

        @JsonProperty("bairro")
        @NotBlank(message = "O bairro é obrigatório")
        @Size(max = 100, message = "O bairro deve ter no máximo 100 caracteres")
        String bairro,

        @JsonProperty("cidade")
        @NotBlank(message = "A cidade é obrigatória")
        @Size(max = 100, message = "A cidade deve ter no máximo 100 caracteres")
        String cidade,

        @JsonProperty("estado")
        @NotBlank(message = "O estado é obrigatório")
        @Size(min = 2, max = 2, message = "O estado deve ser a sigla (ex: BA)")
        String estado,

        @JsonProperty("cep")
        @NotBlank(message = "O CEP é obrigatório")
        @Size(max = 20, message = "O CEP deve ter no máximo 20 caracteres")
        String cep,

        @JsonProperty("latitude")
        String latitude,

        @JsonProperty("longitude")
        String longitude
) {}