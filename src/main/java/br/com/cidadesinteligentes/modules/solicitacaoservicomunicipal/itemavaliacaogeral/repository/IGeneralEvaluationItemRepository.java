package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.itemavaliacaogeral.repository;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.itemavaliacaogeral.model.GeneralEvaluationItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IGeneralEvaluationItemRepository extends JpaRepository<GeneralEvaluationItem, Long> {
    Page<GeneralEvaluationItem> findAllByRequestId(UUID requestId, Pageable pageable);
}
