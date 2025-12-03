package br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.repository;

import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.model.Perfil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;


/**
 * Repositório de acesso a dados para a entidade {@link Perfil}.
 *
 * @author Jorge Roberto, Giovane Neves
 */
public interface IPerfilRepository extends JpaRepository<Perfil, UUID> {

    /**
     * Obtém todos os perfis vinculados a um usuário pelo ID do usuário passado como parâmetro
     *
     * @author Giovane Neves
     * @param userId O id do usuário
     * @return Uma lista dos perfis vinculados ao usuário
     */
    @Query("SELECT p FROM Perfil p WHERE p.usuario.id = :usuarioId")
    Page<Perfil> findAllByUserId(@Param("usuarioId") UUID usuarioId, Pageable pageable);


}
