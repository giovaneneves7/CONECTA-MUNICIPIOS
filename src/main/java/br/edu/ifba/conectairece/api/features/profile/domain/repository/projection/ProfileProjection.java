package br.edu.ifba.conectairece.api.features.profile.domain.repository.projection;

import br.edu.ifba.conectairece.api.features.profile.domain.model.Profile;
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
