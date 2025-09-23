package br.edu.ifba.conectairece.api.features.user.domain.repository;

import br.edu.ifba.conectairece.api.features.user.domain.model.User;
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

}
