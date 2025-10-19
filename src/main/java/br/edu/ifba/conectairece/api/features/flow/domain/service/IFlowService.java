package br.edu.ifba.conectairece.api.features.flow.domain.service;

import br.edu.ifba.conectairece.api.features.flow.domain.dto.request.FlowRequestDTO;
import br.edu.ifba.conectairece.api.features.flow.domain.dto.request.FlowStepRequestDTO;
import br.edu.ifba.conectairece.api.features.flow.domain.dto.response.FlowFullDataResponseDTO;
import br.edu.ifba.conectairece.api.features.flow.domain.dto.response.FlowResponseDTO;
import br.edu.ifba.conectairece.api.features.flow.domain.dto.response.FlowStepResponseDTO;
import br.edu.ifba.conectairece.api.features.flow.domain.model.Flow;

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

}
