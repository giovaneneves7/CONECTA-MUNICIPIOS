package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.listener;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.model.Flow;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.model.FlowStep;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.service.IFlowService;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.acompanhamento.dto.request.MonitoringRequestDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.acompanhamento.enums.MonitoringStatus;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.acompanhamento.service.IMonitoringService;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servicomunicipal.model.MunicipalService;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.event.RequestCreatedEvent;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.model.Request;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.etapa.model.Step;
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
    private final IFlowService flowService;
    private final static String FLOW_NOT_DEFINED = "O serviço municipal não possui um fluxo definido.";

    @EventListener
    @Transactional
    public void onRequestCreated(final RequestCreatedEvent event) {

        Request request = event.getRequest();
        MunicipalService municipalService = request.getMunicipalService();

        // TODO: Tonar o 'flow' e 'step' obrigatórios no momento da criação de um Municial Service
        if (municipalService == null || municipalService.getFlow() == null) {
            throw new BusinessException(FLOW_NOT_DEFINED);
        }

        Flow flow = municipalService.getFlow();
        FlowStep firstFlowStep = this.flowService.getFirstFlowStepByFlowUd(flow.getId());
        Step firstStep = firstFlowStep.getStep();

        MonitoringRequestDTO dto = new MonitoringRequestDTO(
                request.getId(),
                firstStep.getId(),
                firstStep.getCode(),
                MonitoringStatus.PENDING
        );

        monitoringService.save(dto);
    }
}
