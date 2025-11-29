package br.com.cidadesinteligentes.modules.gestaomanutencaourbana.gestor.repository;

import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.gestor.model.GestorSolicitacoesManutencaoUrbana;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IGestorManutencaoRepository extends JpaRepository<GestorSolicitacoesManutencaoUrbana, UUID> {
}