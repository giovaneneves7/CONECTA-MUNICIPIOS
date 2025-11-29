package br.com.cidadesinteligentes.modules.gestaomanutencaourbana.categoria.repository;

import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.categoria.model.CategoriaManutencaoUrbana;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoriaManutencaoUrbanaRepository extends JpaRepository<CategoriaManutencaoUrbana, Long> {

    // Método para evitar duplicidade de nomes
    boolean existsByNome(String nome);
}