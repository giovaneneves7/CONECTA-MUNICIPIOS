package br.com.cidadesinteligentes.modules.marcacaoatendimento.ubs.model;

import br.com.cidadesinteligentes.modules.marcacaoatendimento.unidadeatendimento.model.UnidadeAtendimento;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Representa uma Unidade Básica de Saúde (UBS) no sistema.
 * * @author Juan Teles Dias
 */
@Entity
@DiscriminatorValue("UBS")
@Getter
@Setter
@NoArgsConstructor
public class UBS extends UnidadeAtendimento {
}
