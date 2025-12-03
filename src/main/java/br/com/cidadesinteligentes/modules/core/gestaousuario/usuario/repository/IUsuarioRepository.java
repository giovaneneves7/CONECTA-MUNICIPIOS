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
 * Data access repository for the {@link Usuario} entity.
 *
 * @author Jorge Roberto
 */
@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, UUID> {
    @Query("SELECT u FROM Usuario u WHERE u.email = :email")
    Optional<Usuario> findByEmail(@Param("email") String email);

    @Query("SELECT u FROM Usuario u WHERE u.id = :id")
    Optional<Usuario> findById(@Param("id") UUID id);

    /**
     * Finds a paginated list of users who have at least one profile associated with the specified role name.
     * Uses DISTINCT to avoid returning the same user multiple times if they have multiple profiles with the same role (unlikely but safe).
     *
     * @param cargoNome The name of the Role to filter by.
     * @param pageable Pagination and sorting information.
     * @return A Page containing the Users matching the role name criteria.
     * @author Caio Alves
     */
    @Query("SELECT DISTINCT u FROM Usuario u JOIN u.perfis p JOIN p.cargo c WHERE c.nome = :cargoNome")
    Page<Usuario> findByPerfisCargoNome(@Param("cargoNome") String cargoNome, Pageable pageable);

    /**
     * Finds a paginated list of users filtered by their status (e.g., ACTIVE, INACTIVE).
     *
     * @param status The UserStatus enum to filter by.
     * @param pageable Pagination and sorting information.
     * @return A Page containing the Users matching the status.
     * @author Caio Alves
     */
    Page<Usuario> findByStatus(StatusUsuario status, Pageable pageable);

    /**
     * Finds a paginated list of users whose full name (in Person)
     * or CPF (in Person) contains the search term. The name search
     * is case-insensitive.
     *
     * @param term The search term for name or CPF.
     * @param pageable Pagination and sorting information.
     * @return A Page of Users matching the criteria.
     * @author Caio Alves
     */
@Query("SELECT u FROM Usuario u JOIN u.pessoa p " +
       "WHERE lower(p.nomeCompleto) LIKE lower(concat('%', :term, '%')) " +
       "OR p.cpf LIKE concat('%', :term, '%')")
Page<Usuario> findByFullNameOrCpfContaining(@Param("term") String term, Pageable pageable);


    /**
     * Finds a paginated list of users filtered by both their status AND a specific role name
     * associated with one of their profiles.
     *
     * @param cargoNome The name of the Role to filter by.
     * @param status The UserStatus enum to filter by.
     * @param pageable Pagination and sorting information.
     * @return A Page containing the Users matching both criteria.
     * @author Caio Alves
     */
    @Query("SELECT u FROM Usuario u JOIN u.perfis p JOIN p.cargo c " +
            "WHERE c.nome = :cargoNome AND u.status = :status")
    Page<Usuario> findByPerfisCargoNomeAndStatus(
            @Param("cargoNome") String cargoNome,
            @Param("status") StatusUsuario status,
            Pageable pageable
    ); }
