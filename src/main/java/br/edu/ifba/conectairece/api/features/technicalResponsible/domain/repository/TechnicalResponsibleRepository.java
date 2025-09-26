package br.edu.ifba.conectairece.api.features.technicalResponsible.domain.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifba.conectairece.api.features.technicalResponsible.domain.model.TechnicalResponsible;

@Repository
public interface TechnicalResponsibleRepository extends JpaRepository<TechnicalResponsible, UUID> {

}
