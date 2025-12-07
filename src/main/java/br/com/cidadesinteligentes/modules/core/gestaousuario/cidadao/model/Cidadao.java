package br.com.cidadesinteligentes.modules.core.gestaousuario.cidadao.model;

import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.model.Perfil;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;



/**
 * Representação de uma entidade Cidadão que estende a classe base Perfil.
 * Esta classe é usada para armazenar informações específicas do cidadão, incluindo dados do perfil da conta GOV.
 *
 * @author Jorge Roberto, Caio Alves
 */
@Entity
@DiscriminatorValue("cidadao")
public class Cidadao extends Perfil {

}
