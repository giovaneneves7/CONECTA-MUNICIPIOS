package br.com.cidadesinteligentes.modules.core.gestaousuario.pessoa.repository;

import br.com.cidadesinteligentes.modules.core.gestaousuario.pessoa.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Data access repository for the {@link Pessoa} entity.
 *
 * @author Jorge Roberto
 */
public interface IPersonRepository extends JpaRepository<Pessoa, UUID> {
}
