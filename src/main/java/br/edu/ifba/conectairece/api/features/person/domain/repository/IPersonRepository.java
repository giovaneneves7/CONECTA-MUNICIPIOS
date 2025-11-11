package br.edu.ifba.conectairece.api.features.person.domain.repository;

import br.edu.ifba.conectairece.api.features.person.domain.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Data access repository for the {@link Person} entity.
 *
 * @author Jorge Roberto
 */
public interface IPersonRepository extends JpaRepository<Person, UUID> {
}
