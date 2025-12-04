package br.com.cidadesinteligentes.modules.core.gestaousuario.pessoa.repository;

import br.com.cidadesinteligentes.modules.core.gestaousuario.pessoa.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Repository da entidade {@link Pessoa}.
 */
public interface IPessoaRepository extends JpaRepository<Pessoa, UUID> {
}
