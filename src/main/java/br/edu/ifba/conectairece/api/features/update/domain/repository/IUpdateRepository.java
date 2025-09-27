package br.edu.ifba.conectairece.api.features.update.domain.repository;

import br.edu.ifba.conectairece.api.features.update.domain.model.Update;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author Giovane Neves
 */
@Repository
public interface IUpdateRepository extends JpaRepository<Update, UUID> {
}
