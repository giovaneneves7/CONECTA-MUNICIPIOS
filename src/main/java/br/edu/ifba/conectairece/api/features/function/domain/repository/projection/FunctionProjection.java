package br.edu.ifba.conectairece.api.features.function.domain.repository.projection;

import com.fasterxml.jackson.annotation.JsonInclude;
import br.edu.ifba.conectairece.api.features.function.domain.model.Function;
/**
 * Projection interface for the {@link Function} entity.
 * Used to fetch only the required fields from the database,
 * improving performance when the entire entity is not needed.
 *
 * @author Jorge Roberto
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public interface FunctionProjection {
    String getName();
    String getDescription();
}
