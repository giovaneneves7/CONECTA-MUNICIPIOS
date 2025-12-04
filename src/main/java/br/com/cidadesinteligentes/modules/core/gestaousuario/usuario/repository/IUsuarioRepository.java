package br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.repository;

import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.enums.StatusUsuario;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.model.Usuario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository da entidade {@link Usuario}.
 */
@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, UUID> {
    @Query("SELECT u FROM Usuario u WHERE u.email = :email")
    Optional<Usuario> findByEmail(@Param("email") String email);

    @Query("SELECT u FROM Usuario u WHERE u.id = :id")
    Optional<Usuario> findById(@Param("id") UUID id);

    /**
     *
     * Procura/encontra uma lista páginada de usuário que possuem o cargo, passado como referência.
     *
     * @param cargoNome Nome do cargo, que será filtrado.
     * @param pageable paginação.
     * @return Página com as especificações dos cargos buscados.
     * @author Caio Alves
     */
    @Query("SELECT DISTINCT u FROM Usuario u JOIN u.perfis p JOIN p.cargo c WHERE c.nome = :cargoNome")
    Page<Usuario> findByPerfisCargoNome(@Param("cargoNome") String cargoNome, Pageable pageable);

    /**
     * Procura/encontra uma lista páginada de usuários baseado no status
     *
     * @author Caio Alves
     */
    Page<Usuario> findByStatus(StatusUsuario status, Pageable pageable);

    /**
     * Procura/encontra uma lista páginada de usuário os quais o nome ou cpf
     * contenham os termos da consulta.
     *
     * @param term O termo que será buscando, sendo parte do nome ou CPF.
     * @param pageable Paginação
     * @return Pagína que atenda aos requisitos
     * @author Caio Alves
     */
    @Query("SELECT u FROM Usuario u JOIN u.pessoa p " +
           "WHERE lower(p.nomeCompleto) LIKE lower(concat('%', :term, '%')) " +
           "OR p.cpf LIKE concat('%', :term, '%')")
    Page<Usuario> findByNomeCompletoOrCpfContaining(@Param("term") String term, Pageable pageable);


    /**
     * Busca uma lista páginada de usuários, filtrando pelo nome do cargo e status do usuário
     *
     * @param cargoNome O nome do cargo filtrado
     * @param status O UserStatus enum filtrado.
     * @param pageable A paginação filtrada.
     * @return A página que atenda aos requisitos de filtragem.
     * @author Caio Alves
     */
    @Query("SELECT u FROM Usuario u JOIN u.perfis p JOIN p.cargo c " +
            "WHERE c.nome = :cargoNome AND u.status = :status")
    Page<Usuario> findByPerfisCargoNomeAndStatus(
            @Param("cargoNome") String cargoNome,
            @Param("status") StatusUsuario status,
            Pageable pageable
    ); }
