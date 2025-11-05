package br.edu.ifba.conectairece.api.features.comment.domain.repository;

import br.edu.ifba.conectairece.api.features.comment.domain.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
