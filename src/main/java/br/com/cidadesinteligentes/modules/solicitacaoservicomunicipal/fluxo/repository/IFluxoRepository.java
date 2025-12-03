package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.repository;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.model.Fluxo;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servicomunicipal.model.ServicoMunicipal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repositório responsável pelo acesso aos dados da entidade {@link Fluxo}.
 * 
 * <p>Fornece operações de persistência padrão através do Spring Data JPA,
 * além de métodos específicos para recuperar fluxos associados a serviços municipais.</p>
 *
 * <p><b>Regras:</b></p>
 * <ul>
 *   <li>Os métodos seguem a nomenclatura padrão do Spring (findBy..., existsBy..., etc.).</li>
 *   <li>As consultas são geradas automaticamente com base nos nomes dos métodos.</li>
 * </ul>
 *
 * @author: Giovane Neves, Andesson Reis
 */
@Repository
public interface IFluxoRepository extends JpaRepository<Fluxo, UUID> {

    /**
     * Busca um fluxo associado a um serviço municipal específico.
     *
     * @param servicoMunicipal entidade de serviço municipal
     * @return fluxo vinculado ao serviço informado, ou null se não existir
     */
    Fluxo findByServicoMunicipal(ServicoMunicipal servicoMunicipal);

    List<Fluxo> findAllByServicoMunicipal(ServicoMunicipal servicoMunicipal);

}
