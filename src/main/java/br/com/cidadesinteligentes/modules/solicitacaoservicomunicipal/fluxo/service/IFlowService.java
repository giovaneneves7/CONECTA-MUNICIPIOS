package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.service;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.dto.request.FlowRequestDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.dto.request.FlowStepRequestDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.dto.response.FlowFullDataResponseDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.dto.response.FlowResponseDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.dto.response.FlowStepResponseDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.model.FlowStep;

import java.util.List;
import java.util.UUID;

/**
 * @author Giovane Neves
 */
public interface IFlowService {

    FlowResponseDTO createFlow(final FlowRequestDTO dto);
    FlowStepResponseDTO createFlowStep(final FlowStepRequestDTO dto);
    List<FlowFullDataResponseDTO> getAllFlows();
    FlowFullDataResponseDTO getFlowById(final UUID id);
    FlowFullDataResponseDTO getFlowByMunicipalServiceId(final Long id);
    FlowStep getFirstFlowStepByFlowUd(final UUID flowId);
}
