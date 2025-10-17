package br.edu.ifba.conectairece.api.features.flow.domain.service;

import br.edu.ifba.conectairece.api.features.flow.domain.dto.response.FlowFullDataResponseDTO;
import br.edu.ifba.conectairece.api.features.flow.domain.dto.response.FlowResponseDTO;
import br.edu.ifba.conectairece.api.features.flow.domain.dto.response.FlowStepResponseDTO;
import br.edu.ifba.conectairece.api.features.flow.domain.model.Flow;
import br.edu.ifba.conectairece.api.features.flow.domain.model.FlowStep;

import java.util.List;

/**
 * @author Giovane Neves
 */
public interface IFlowService {

    FlowResponseDTO createFlow(final Flow flow);
    FlowStepResponseDTO createFlowStep(final FlowStep flowStep);
    List<FlowFullDataResponseDTO> getAllFlows();

}
