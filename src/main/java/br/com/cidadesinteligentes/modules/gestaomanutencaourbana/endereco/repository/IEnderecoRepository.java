package br.com.cidadesinteligentes.modules.gestaomanutencaourbana.endereco.repository;

import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.endereco.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEnderecoRepository extends JpaRepository<Endereco, Long> {
    // Métodos de consulta personalizados podem ser adicionados aqui se necessário
}