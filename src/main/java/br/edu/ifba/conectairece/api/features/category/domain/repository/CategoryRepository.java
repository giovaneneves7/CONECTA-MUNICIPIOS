package br.edu.ifba.conectairece.api.features.category.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifba.conectairece.api.features.category.domain.model.Category;

import java.util.Optional;

/**
 * Data access repository for the {@link Category} entity.
 * Provides CRUD operations and database interaction methods for categories.
 *
 * @author Caio Alves
 */

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{
    Optional<Category> findByName(String name);
}
