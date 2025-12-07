package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servicomunicipal.service;

import java.util.List;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servicomunicipal.dto.request.MunicipalServiceRequestDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servicomunicipal.dto.response.MunicipalServiceResponseDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servicomunicipal.model.ServicoMunicipal;
import org.springframework.data.domain.Pageable;

/**
 * Define o contrato de operações para gerenciamento da entidade {@link ServicoMunicipal}.
 * Fornece métodos para criação, consulta e exclusão de serviços municipais.
 *
 * @author: Caio Alves, Andesson Reis
 */
public interface IServicoMunicipalService {

    /**
     * Salva um novo serviço municipal.
     *
     * @param dto dados enviados contendo informações do serviço
     * @return DTO com os dados salvos
     */
    MunicipalServiceResponseDTO save(MunicipalServiceRequestDTO dto);

    /**
     * Recupera todos os serviços municipais de forma paginada.
     *
     * @param pageable parâmetros de paginação
     * @return lista de serviços municipais
     */
    List<MunicipalServiceResponseDTO> findAll(Pageable pageable);

    /**
     * Busca um serviço municipal pelo seu identificador.
     *
     * @param id identificador do serviço
     * @return DTO do serviço encontrado
     */
    MunicipalServiceResponseDTO findById(Long id);

    /**
     * Exclui um serviço municipal pelo seu identificador.
     *
     * @param id identificador do serviço
     */
    void delete(Long id);

}
