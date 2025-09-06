package br.edu.ifba.conectairece.api.features.function.domain.repository;

import br.edu.ifba.conectairece.api.features.function.domain.model.Function;
import br.edu.ifba.conectairece.api.features.function.domain.repository.projection.FunctionProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Data access repository for the {@link Function} entity.
 *
 * @author Jorge Roberto
 */
public interface FunctionRepository extends JpaRepository<Function, Long> {
    /**
     * Retrieves a paginated list of {@link FunctionProjection},
     * returning only the required fields (name and description).
     * <p>
     * This method improves performance when the full {@link Function}
     * entity is not needed.
     * </p>
     *
     * @param pageable the pagination information
     * @return a page containing projected functions
     */
    Page<FunctionProjection> findAllProjectedBy(Pageable pageable);

    Optional<Function> findByName(String name);

    Optional<Function> findByNameAndIdNot(String name, Long id);
}
