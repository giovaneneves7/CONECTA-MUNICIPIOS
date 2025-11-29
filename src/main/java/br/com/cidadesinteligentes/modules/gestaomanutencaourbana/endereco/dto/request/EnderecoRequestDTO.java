package br.com.cidadesinteligentes.modules.gestaomanutencaourbana.endereco.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EnderecoRequestDTO {

    @NotBlank(message = "A rua é obrigatória")
    @Size(max = 150)
    private String rua;

    @NotBlank(message = "O número é obrigatório")
    @Size(max = 20)
    private String numero;

    @NotBlank(message = "O bairro é obrigatório")
    @Size(max = 100)
    private String bairro;

    @NotBlank(message = "A cidade é obrigatória")
    @Size(max = 100)
    private String cidade;

    @NotBlank(message = "O estado é obrigatório")
    @Size(min = 2, max = 2, message = "O estado deve ser a sigla (ex: BA)")
    private String estado;

    @NotBlank(message = "O CEP é obrigatório")
    @Size(max = 20)
    private String cep;

    private String latitude;
    private String longitude;
}