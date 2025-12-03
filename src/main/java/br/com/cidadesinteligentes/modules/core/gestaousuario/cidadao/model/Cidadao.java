package br.com.cidadesinteligentes.modules.core.gestaousuario.cidadao.model;

import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.model.Perfil;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;


/**
 * Representação de uma entidade Cidadão que estende a classe base Perfil.
 * Esta classe é usada para armazenar informações específicas do cidadão, incluindo dados do perfil governamental.
 *
 * @author Jorge Roberto, Caio Alves
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("CIDADAO")
public class Cidadao extends Perfil {

    @Column(name = "perfil_gov_snapshot", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private String perfilGovSnapshot;
}
