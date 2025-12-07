package br.com.cidadesinteligentes.modules.core.gestaousuario.pessoa.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * DTO de resposta com os dados de Pessoas, sem o ID
 * @author Jorge Roberto
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PessoaResponseDTO {

    @JsonProperty("cpf")
    private String cpf;

    @JsonProperty("nomeCompleto")
    private String nomeCompleto;

    @JsonProperty("dataNascimento")
    private LocalDate dataNascimento;

    @JsonProperty("genero")
    private String genero;
}
