package br.edu.ifba.conectairece.api.features.flow.domain.service;

import br.edu.ifba.conectairece.api.features.flow.domain.dto.response.FlowResponseDTO;
import br.edu.ifba.conectairece.api.features.flow.domain.dto.response.FlowStepResponseDTO;
import br.edu.ifba.conectairece.api.features.flow.domain.model.Flow;
import br.edu.ifba.conectairece.api.features.flow.domain.model.FlowStep;
import br.edu.ifba.conectairece.api.features.flow.domain.repository.IFlowRepository;
import br.edu.ifba.conectairece.api.features.flow.domain.repository.IFlowStepRepository;
import br.edu.ifba.conectairece.api.infraestructure.util.ObjectMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Giovane Neves
 */
@Service
@RequiredArgsConstructor
public class FlowService implements IFlowService {

    private final ObjectMapperUtil objectMapperUtil;
    private final IFlowRepository flowRepository;
    private final IFlowStepRepository flowStepRepository;

    @Override
    public FlowResponseDTO createFlow(Flow flow) {

        return this.objectMapperUtil.mapToRecord(this.flowRepository.save(flow),  FlowResponseDTO.class);

    }

    @Override
    public FlowStepResponseDTO createFlowStep(FlowStep flowStep) {

        return this.objectMapperUtil.mapToRecord(this.flowStepRepository.save(flowStep), FlowStepResponseDTO.class);

    }

}
