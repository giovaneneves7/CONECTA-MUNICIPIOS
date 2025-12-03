package br.com.cidadesinteligentes.modules.marcacaoatendimento.servico.repository;

import br.com.cidadesinteligentes.modules.marcacaoatendimento.servico.model.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * * @author Juan Teles Dias
 */
@Repository
public interface IServicoRepository extends JpaRepository<Servico, UUID> {

}