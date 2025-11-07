package br.edu.ifba.conectairece.api.features.user.domain.repository;

import br.edu.ifba.conectairece.api.features.auth.domain.enums.UserStatus;
import br.edu.ifba.conectairece.api.features.user.domain.model.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Data access repository for the {@link User} entity.
 *
 * @author Jorge Roberto
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);

    @Query("SELECT u FROM User u WHERE u.id = :id")
    Optional<User> findById(@Param("id") UUID id);

    /**
     * Finds a paginated list of users who have at least one profile associated with the specified role name.
     * Uses DISTINCT to avoid returning the same user multiple times if they have multiple profiles with the same role (unlikely but safe).
     *
     * @param roleName The name of the Role to filter by.
     * @param pageable Pagination and sorting information.
     * @return A Page containing the Users matching the role name criteria.
     * @author Caio Alves
     */
    @Query("SELECT u FROM User u JOIN u.profiles p JOIN p.role r WHERE r.name = :roleName")
    Page<User> findByProfileRoleName(@Param("roleName") String roleName, Pageable pageable);

    /**
     * Finds a paginated list of users filtered by their status (e.g., ACTIVE, INACTIVE).
     *
     * @param status The UserStatus enum to filter by.
     * @param pageable Pagination and sorting information.
     * @return A Page containing the Users matching the status.
     * @author Caio Alves
     */
    Page<User> findByStatus(UserStatus status, Pageable pageable);

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
    @Query("SELECT u FROM User u JOIN u.person p " +
           "WHERE lower(p.fullName) LIKE lower(concat('%', :term, '%')) " +
           "OR p.cpf LIKE concat('%', :term, '%')")
    Page<User> findByFullNameOrCpfContaining(@Param("term") String term, Pageable pageable);

    /**
     * Finds a paginated list of users filtered by both their status AND a specific role name
     * associated with one of their profiles.
     *
     * @param roleName The name of the Role to filter by.
     * @param status The UserStatus enum to filter by.
     * @param pageable Pagination and sorting information.
     * @return A Page containing the Users matching both criteria.
     * @author Caio Alves
     */
    @Query("SELECT u FROM User u JOIN u.profiles p JOIN p.role r " +
           "WHERE r.name = :roleName AND u.status = :status")
    Page<User> findByProfileRoleNameAndStatus(@Param("roleName") String roleName, @Param("status") UserStatus status, Pageable pageable);
}
