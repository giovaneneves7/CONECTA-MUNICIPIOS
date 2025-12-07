package br.com.cidadesinteligentes.modules.core.gestaousuario.cargo.repository;

import br.com.cidadesinteligentes.modules.core.gestaousuario.cargo.model.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositório de acesso a dados para a entidade {@link Cargo}.
 *
 * @author Jorge Roberto, Caio Alves
 */
public interface ICargoRepository extends JpaRepository<Cargo, Long> {
    Optional<Cargo> findByNome(String nome);
}
