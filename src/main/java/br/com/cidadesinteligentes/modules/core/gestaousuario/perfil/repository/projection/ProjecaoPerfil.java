package br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.repository.projection;

import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.model.Perfil;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * interface de projeção para a entidade {@link Perfil}.
 *
 * @author Jorge Roberto
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public interface ProjecaoPerfil {
    String getType();
    String getImageUrl();
}
