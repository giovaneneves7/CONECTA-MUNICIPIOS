package br.edu.ifba.conectairece.api.features.request.domain.listener;

import br.edu.ifba.conectairece.api.features.flow.domain.model.Flow;
import br.edu.ifba.conectairece.api.features.flow.domain.model.FlowStep;
import br.edu.ifba.conectairece.api.features.flow.domain.service.IFlowService;
import br.edu.ifba.conectairece.api.features.monitoring.domain.dto.request.MonitoringRequestDTO;
import br.edu.ifba.conectairece.api.features.monitoring.domain.enums.MonitoringStatus;
import br.edu.ifba.conectairece.api.features.monitoring.domain.service.IMonitoringService;
import br.edu.ifba.conectairece.api.features.municipalservice.domain.model.MunicipalService;
import br.edu.ifba.conectairece.api.features.request.domain.event.RequestCreatedEvent;
import br.edu.ifba.conectairece.api.features.request.domain.model.Request;

import br.edu.ifba.conectairece.api.features.step.domain.model.Step;
import br.edu.ifba.conectairece.api.infraestructure.exception.BusinessException;
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
