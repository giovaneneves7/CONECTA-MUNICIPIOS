package br.com.cidadesinteligentes.modules.core.gestaousuario.permissao.repository;

import br.com.cidadesinteligentes.modules.core.gestaousuario.permissao.model.Permissao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IPermissaoRepository extends JpaRepository<Permissao, UUID> {
    Optional<Permissao> findByNome(String nome);
}
