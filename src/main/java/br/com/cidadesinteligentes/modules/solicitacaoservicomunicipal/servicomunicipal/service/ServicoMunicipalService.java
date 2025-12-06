package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servicomunicipal.service;

import java.util.List;

import br.com.cidadesinteligentes.infraestructure.exception.BusinessException;
import br.com.cidadesinteligentes.infraestructure.exception.BusinessExceptionMessage;
import br.com.cidadesinteligentes.infraestructure.util.ObjectMapperUtil;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.model.Fluxo;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.repository.IAtividadeFluxoRepository;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.repository.IFluxoRepository;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servicomunicipal.dto.request.MunicipalServiceRequestDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servicomunicipal.dto.response.MunicipalServiceResponseDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servicomunicipal.model.ServicoMunicipal;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servicomunicipal.repository.IServicoMunicipalRepository;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.model.Solicitacao;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.repository.ISolicitacaoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Serviço responsável pelo gerenciamento da entidade {@link ServicoMunicipal}.
 * Implementa operações de criação, consulta e exclusão de serviços municipais.
 *
 * <p>Principais funcionalidades:
 * <ul>
 *   <li>Salvar novos serviços municipais</li>
 *   <li>Listar serviços com paginação</li>
 *   <li>Buscar serviços pelo ID</li>
 *   <li>Excluir serviços</li>
 * </ul>
 *
 * @author: Caio Alves, Andesson Reis
 */
@Service
@RequiredArgsConstructor
public class ServicoMunicipalService implements IServicoMunicipalService {

    private final ObjectMapperUtil objectMapperUtil;
    private final IServicoMunicipalRepository servicoMunicipalRepository;
    private final IFluxoRepository fluxoRepository;
    private final IAtividadeFluxoRepository atividadeFluxoRepository;
    private final ISolicitacaoRepository solicitacaoRepository;

    /**
     * Salva um novo serviço municipal no banco de dados.
     *
     * @param dto dados de entrada contendo nome e descrição
     * @return DTO com informações do serviço salvo
     */
    @Override
    public MunicipalServiceResponseDTO save(MunicipalServiceRequestDTO dto) {

        ServicoMunicipal entity = new ServicoMunicipal();
        entity.setNome(dto.nome());
        entity.setDescricao(dto.descricao());

        ServicoMunicipal saved = servicoMunicipalRepository.save(entity);

        return new MunicipalServiceResponseDTO(
                saved.getId(),
                saved.getNome(),
                saved.getDescricao()
        );
    }

    /**
     * Recupera todos os serviços municipais com paginação.
     *
     * @param pageable informações de paginação
     * @return lista de DTOs representando os serviços municipais
     */
    @Override
    public List<MunicipalServiceResponseDTO> findAll(Pageable pageable) {

        Page<ServicoMunicipal> page = servicoMunicipalRepository.findAll(pageable);

        return page.stream()
                .map(service ->
                        new MunicipalServiceResponseDTO(
                                service.getId(),
                                service.getNome(),
                                service.getDescricao()
                        )
                )
                .toList();
    }

    /**
     * Busca um serviço municipal pelo ID.
     *
     * @param id identificador do serviço
     * @return DTO representando o serviço encontrado
     */
    @Override
    public MunicipalServiceResponseDTO findById(Long id) {

        ServicoMunicipal entity = servicoMunicipalRepository.findById(id)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        return objectMapperUtil.mapToRecord(entity, MunicipalServiceResponseDTO.class);
    }

    /**
     * Remove um serviço municipal pelo ID.
     *
     * @param id identificador do serviço
        */
    @Override
    @Transactional
    public void delete(Long id) {

        ServicoMunicipal servico = servicoMunicipalRepository.findById(id)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        List<Fluxo> fluxos = fluxoRepository.findAllByServicoMunicipal(servico);

        for (Fluxo fluxo : fluxos) {
            atividadeFluxoRepository.deleteAllByFluxoId(fluxo.getId());

            fluxoRepository.delete(fluxo);
        }
        servico.setFluxo(null); 

        List<Solicitacao> solicitacoes = solicitacaoRepository.findAllByServicoMunicipalId(servico.getId());

        solicitacaoRepository.deleteAll(solicitacoes);

        servicoMunicipalRepository.delete(servico);
    }

}
