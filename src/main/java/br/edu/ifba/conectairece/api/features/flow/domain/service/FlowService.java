package br.edu.ifba.conectairece.api.features.flow.domain.service;

import br.edu.ifba.conectairece.api.features.flow.domain.dto.request.FlowRequestDTO;
import br.edu.ifba.conectairece.api.features.flow.domain.dto.request.FlowStepRequestDTO;
import br.edu.ifba.conectairece.api.features.flow.domain.dto.response.FlowFullDataResponseDTO;
import br.edu.ifba.conectairece.api.features.flow.domain.dto.response.FlowResponseDTO;
import br.edu.ifba.conectairece.api.features.flow.domain.dto.response.FlowStepResponseDTO;
import br.edu.ifba.conectairece.api.features.flow.domain.model.Flow;
import br.edu.ifba.conectairece.api.features.flow.domain.model.FlowStep;
import br.edu.ifba.conectairece.api.features.flow.domain.repository.IFlowRepository;
import br.edu.ifba.conectairece.api.features.flow.domain.repository.IFlowStepRepository;
import br.edu.ifba.conectairece.api.features.municipalservice.domain.model.MunicipalService;
import br.edu.ifba.conectairece.api.features.municipalservice.domain.repository.MunicipalServiceRepository;
import br.edu.ifba.conectairece.api.features.step.domain.dto.response.StepFullDataResponseDTO;
import br.edu.ifba.conectairece.api.features.step.domain.model.Step;
import br.edu.ifba.conectairece.api.features.step.domain.repository.IStepRepository;
import br.edu.ifba.conectairece.api.infraestructure.exception.BusinessException;
import br.edu.ifba.conectairece.api.infraestructure.exception.BusinessExceptionMessage;
import br.edu.ifba.conectairece.api.infraestructure.util.ObjectMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

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
    private final MunicipalServiceRepository municipalServiceRepository;

    @Override
    public FlowResponseDTO createFlow(FlowRequestDTO dto) {

        MunicipalService municipalService = this.municipalServiceRepository.findById(dto.municipalServiceId())
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        Flow flow = this.objectMapperUtil.map(dto, Flow.class);
        flow.setMunicipalService(municipalService);

        return this.objectMapperUtil.mapToRecord(this.flowRepository.save(flow),  FlowResponseDTO.class);

    }

    @Override
    public FlowStepResponseDTO createFlowStep(FlowStepRequestDTO dto) {

        Flow flow = this.flowRepository.findById(dto.flowId())
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        Step step = this.stepRepository.findById(dto.stepId())
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        FlowStep flowStep = new FlowStep();
        flowStep.setFlow(flow);
        flowStep.setStep(step);
        flowStep.setStepOrder(dto.stepOrder());

        return this.objectMapperUtil.mapToRecord(this.flowStepRepository.save(flowStep), FlowStepResponseDTO.class);

    }

    @Override
    public List<FlowFullDataResponseDTO> getAllFlows() {

        List<Flow> flows = flowRepository.findAll();
        List<FlowFullDataResponseDTO> responseList = new ArrayList<>();

        for(Flow flow : flows) {

            List<StepFullDataResponseDTO> stepDTOs = this.findStepsByFlowId(flow.getId());

            FlowFullDataResponseDTO flowDTO = new FlowFullDataResponseDTO(
                    flow.getId(),
                    flow.getName(),
                    stepDTOs,
                    flow.getMunicipalService().getId()
            );

            responseList.add(flowDTO);

        }
        return responseList;

    }

    @Override
    public FlowFullDataResponseDTO getFlowById(final UUID id) {

        Flow flow = this.flowRepository.findById(id)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        List<StepFullDataResponseDTO> stepDTOs = this.findStepsByFlowId(flow.getId());

        return new FlowFullDataResponseDTO(
                flow.getId(),
                flow.getName(),
                stepDTOs,
                flow.getMunicipalService().getId()
        );

    }

    @Override
    public FlowFullDataResponseDTO getFlowByMunicipalServiceId(Long id) {

        MunicipalService municipalService = this.municipalServiceRepository.findById(id)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));
        Flow flow = this.flowRepository.findByMunicipalService(municipalService);

        List<StepFullDataResponseDTO> stepDTOs = this.findStepsByFlowId(flow.getId());

        return new FlowFullDataResponseDTO(
                flow.getId(),
                flow.getName(),
                stepDTOs,
                flow.getMunicipalService().getId()
        );

    }

    private List<StepFullDataResponseDTO> findStepsByFlowId(final UUID flowId){
        List<Step> steps = stepRepository.findAllByFlowId(flowId);

        return steps.stream()
                .map(step -> {

                    FlowStep flowStep = flowStepRepository.findByFlowIdAndStepId(flowId, step.getId())
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
    }

}
