package br.edu.ifba.conectairece.api.features.flow.domain.service;

import br.edu.ifba.conectairece.api.features.flow.domain.dto.response.FlowFullDataResponseDTO;
import br.edu.ifba.conectairece.api.features.flow.domain.dto.response.FlowResponseDTO;
import br.edu.ifba.conectairece.api.features.flow.domain.dto.response.FlowStepResponseDTO;
import br.edu.ifba.conectairece.api.features.flow.domain.model.Flow;
import br.edu.ifba.conectairece.api.features.flow.domain.model.FlowStep;
import br.edu.ifba.conectairece.api.features.flow.domain.repository.IFlowRepository;
import br.edu.ifba.conectairece.api.features.flow.domain.repository.IFlowStepRepository;
import br.edu.ifba.conectairece.api.features.step.domain.dto.response.StepFullDataResponseDTO;
import br.edu.ifba.conectairece.api.features.step.domain.model.Step;
import br.edu.ifba.conectairece.api.features.step.domain.repository.IStepRepository;
import br.edu.ifba.conectairece.api.infraestructure.util.ObjectMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author Giovane Neves
 */
@Service
@RequiredArgsConstructor
public class FlowService implements IFlowService {

    private final ObjectMapperUtil objectMapperUtil;
    private final IFlowRepository flowRepository;
    private final IFlowStepRepository flowStepRepository;
    private final IStepRepository stepRepository;

    @Override
    public FlowResponseDTO createFlow(Flow flow) {

        return this.objectMapperUtil.mapToRecord(this.flowRepository.save(flow),  FlowResponseDTO.class);

    }

    @Override
    public FlowStepResponseDTO createFlowStep(FlowStep flowStep) {

        return this.objectMapperUtil.mapToRecord(this.flowStepRepository.save(flowStep), FlowStepResponseDTO.class);

    }

    @Override
    public List<FlowFullDataResponseDTO> getAllFlows() {

        List<Flow> flows = flowRepository.findAll();
        List<FlowFullDataResponseDTO> responseList = new ArrayList<>();

        for(Flow flow : flows) {

            List<Step> steps = stepRepository.findAllByFlowId(flow.getId());

            List<StepFullDataResponseDTO> stepDTOs = steps.stream()
                    .map(step -> {

                        FlowStep flowStep = flowStepRepository.findByFlowIdAndStepId(flow.getId(), step.getId())
                                .orElse(null);
                        long order = (flowStep != null) ? flowStep.getStepOrder() : 0;

                        return new StepFullDataResponseDTO(
                                step.getId(),
                                step.getName(),
                                step.getCode(),
                                step.getImageUrl(),
                                order
                        );
                    })
                    .sorted(Comparator.comparingLong(StepFullDataResponseDTO::order))
                    .toList();

            FlowFullDataResponseDTO flowDTO = new FlowFullDataResponseDTO(
                    flow.getId(),
                    flow.getName(),
                    stepDTOs
            );

            responseList.add(flowDTO);

        }
        return responseList;

    }

}
