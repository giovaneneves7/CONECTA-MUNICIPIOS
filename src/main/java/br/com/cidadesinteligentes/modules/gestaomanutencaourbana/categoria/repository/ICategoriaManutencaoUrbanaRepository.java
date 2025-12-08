package br.com.cidadesinteligentes.modules.gestaomanutencaourbana.categoria.repository;

import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.categoria.model.CategoriaManutencaoUrbana;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoriaManutencaoUrbanaRepository extends JpaRepository<CategoriaManutencaoUrbana, Long> {

    // Verifica se já existe uma categoria com o nome fornecido, evitando duplicidade
    boolean existsByNome(String nome);
}