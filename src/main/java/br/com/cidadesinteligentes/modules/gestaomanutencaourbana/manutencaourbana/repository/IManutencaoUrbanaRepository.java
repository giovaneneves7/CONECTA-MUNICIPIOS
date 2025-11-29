package br.com.cidadesinteligentes.modules.gestaomanutencaourbana.manutencaourbana.repository;

import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.manutencaourbana.model.ManutencaoUrbana;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IManutencaoUrbanaRepository extends JpaRepository<ManutencaoUrbana, Long> {
}