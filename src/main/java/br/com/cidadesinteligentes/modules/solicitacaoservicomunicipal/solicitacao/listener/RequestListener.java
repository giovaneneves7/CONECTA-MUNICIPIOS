package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.listener;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.model.Fluxo;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.model.EtapaFluxo;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.service.IFluxoService;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.acompanhamento.dto.request.MonitoringRequestDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.acompanhamento.enums.MonitoringStatus;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.acompanhamento.service.IMonitoringService;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servicomunicipal.model.ServicoMunicipal;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.event.RequestCreatedEvent;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.model.Request;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.etapa.model.Etapa;
import br.com.cidadesinteligentes.infraestructure.exception.BusinessException;
import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author Giovane Neves
 */
@Component
@RequiredArgsConstructor
public class RequestListener {

    private final IMonitoringService monitoringService;
    private final IFluxoService flowService;
    private final static String FLOW_NOT_DEFINED = "O serviço municipal não possui um fluxo definido.";

    @EventListener
    @Transactional
    public void onRequestCreated(final RequestCreatedEvent event) {

        Request request = event.getRequest();
        ServicoMunicipal municipalService = request.getServicoMunicipal();

        // TODO: Tonar o 'flow' e 'step' obrigatórios no momento da criação de um Municial Service
        if (municipalService == null || municipalService.getFluxo() == null) {
            throw new BusinessException(FLOW_NOT_DEFINED);
        }

        Fluxo fluxo = municipalService.getFluxo();
        EtapaFluxo firstFlowStep = this.flowService.buscarPrimeiraEtapaFluxoPorFluxoId(fluxo.getId());
        Etapa firstStep = firstFlowStep.getEtapa();

        MonitoringRequestDTO dto = new MonitoringRequestDTO(
                request.getId(),
                firstStep.getId(),
                firstStep.getCode(),
                MonitoringStatus.PENDING
        );

        monitoringService.save(dto);
    }
}
