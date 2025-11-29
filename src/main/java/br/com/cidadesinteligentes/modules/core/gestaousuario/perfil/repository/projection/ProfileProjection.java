package br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.repository.projection;

import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.model.Profile;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Projection interface for the {@link Profile} entity.
 *
 * @author Jorge Roberto
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public interface ProfileProjection {
    String getType();
    String getImageUrl();
}
