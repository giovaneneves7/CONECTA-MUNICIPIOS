package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servicomunicipal.repository;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servicomunicipal.model.ServicoMunicipal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório responsável pelo acesso aos dados da entidade {@link ServicoMunicipal}.
 * Fornece operações de CRUD e consultas relacionadas aos serviços municipais
 * disponibilizados pela prefeitura.
 *
 * <p>Este repositório é utilizado pelos casos de uso que gerenciam
 * criação, atualização e recuperação dos serviços oferecidos.</p>
 *
 * <p>@author: Andesson Reis, Caio Alves</p>
 */
@Repository
public interface IServicoMunicipalRepository extends JpaRepository<ServicoMunicipal, Long> {

}
