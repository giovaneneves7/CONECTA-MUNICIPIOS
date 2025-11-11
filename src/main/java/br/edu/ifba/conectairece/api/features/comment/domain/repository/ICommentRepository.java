package br.edu.ifba.conectairece.api.features.comment.domain.repository;

import br.edu.ifba.conectairece.api.features.comment.domain.model.Comment;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICommentRepository extends JpaRepository<Comment, Long> {
/**
     * Encontra o primeiro (mais recente) comentário associado a um 'requirementId',
     * ordenando pelo ID de forma decrescente.
     * 
     *
     * @param requirementId O ID do Requirement.
     * @return um Optional contendo o Comment mais recente, se existir.
     * @author Andesson Reis
     */
    Optional<Comment> findFirstByRequirementIdOrderByIdDesc(Long requirementId);
}

