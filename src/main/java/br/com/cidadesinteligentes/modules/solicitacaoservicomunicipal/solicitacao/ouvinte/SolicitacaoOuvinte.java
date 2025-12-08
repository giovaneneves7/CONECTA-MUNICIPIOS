package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.ouvinte;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.model.Fluxo;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.model.AtividadeFluxo;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.service.IFluxoService;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.acompanhamento.dto.request.AcompanhamentoRequestDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.acompanhamento.enums.AcompanhamentoStatus;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.acompanhamento.service.IAcompanhamentoService;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.atividade.model.Atividade;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servicomunicipal.model.ServicoMunicipal;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.evento.SolicitacaoCriadaEvento;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.model.Solicitacao;
import br.com.cidadesinteligentes.infraestructure.exception.BusinessException;
import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Ouvinte responsável por tratar eventos relacionados à entidade Solicitação.
 *
 * <p>Este componente escuta eventos do sistema (como a criação de uma solicitação)
 * e executa a lógica de negócio reativa necessária, como iniciar o primeiro
 * acompanhamento baseado no fluxo do serviço municipal.</p>
 *
 * @author Giovane Neves, Andesson Reis
 */
@Component
@RequiredArgsConstructor
public class SolicitacaoOuvinte {

    private final IAcompanhamentoService acompanhamentoService;
    private final IFluxoService fluxoService;
    private static final String FLUXO_NAO_DEFINIDO = "O serviço municipal não possui um fluxo definido.";

    /**
     * Manipula o evento de criação de uma nova solicitação.
     *
     * <p>Ao detectar que uma solicitação foi criada, este método identifica o fluxo
     * associado ao serviço municipal, recupera a primeira atividade desse fluxo e cria
     * o primeiro registro de acompanhamento com status PENDENTE.</p>
     *
     * @param evento O evento contendo os dados da solicitação recém-criada.
     * @throws BusinessException Se o serviço municipal não possuir um fluxo configurado.
     */
    @EventListener
    @Transactional
    public void aoCriarSolicitacao(final SolicitacaoCriadaEvento evento) {

        Solicitacao solicitacao = evento.getSolicitacao();
        ServicoMunicipal servicoMunicipal = solicitacao.getServicoMunicipal();

        // TODO: Tornar o 'fluxo' e 'atividade' obrigatórios no momento da criação de um Serviço Municipal
        if (servicoMunicipal == null || servicoMunicipal.getFluxo() == null) {
            throw new BusinessException(FLUXO_NAO_DEFINIDO);
        }

        Fluxo fluxo = servicoMunicipal.getFluxo();
        AtividadeFluxo primeiraAtividadeFluxo = this.fluxoService.buscarPrimeiraAtividadeDoFluxoPorFluxoId(fluxo.getId());
        Atividade primeiraAtividade = primeiraAtividadeFluxo.getAtividade();

        AcompanhamentoRequestDTO dto = new AcompanhamentoRequestDTO(
                solicitacao.getId(),
                primeiraAtividade.getId(),
                primeiraAtividade.getCodigo(),
                AcompanhamentoStatus.PENDENTE
        );

        acompanhamentoService.save(dto);
    }
}