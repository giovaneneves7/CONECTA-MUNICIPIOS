package br.com.cidadesinteligentes.modules.marcacaoatendimento.servico.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

/**
 * DTO para criação e atualização de Serviço.
 * @author Juan Teles Dias
 */
public record ServicoRequestDTO(

        @NotBlank(message = "O nome do serviço é obrigatório.")
        String nome,

        @NotNull(message = "O ID da unidade é obrigatório.")
        UUID unidadeId

) {}