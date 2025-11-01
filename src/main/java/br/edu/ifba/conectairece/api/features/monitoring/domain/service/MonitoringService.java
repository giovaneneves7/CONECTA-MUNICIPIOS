package br.edu.ifba.conectairece.api.features.monitoring.domain.service;

import br.edu.ifba.conectairece.api.features.flow.domain.model.FlowStep;
import br.edu.ifba.conectairece.api.features.flow.domain.repository.IFlowStepRepository;
import br.edu.ifba.conectairece.api.features.monitoring.domain.dto.request.MonitoringRequestDTO;
import br.edu.ifba.conectairece.api.features.monitoring.domain.dto.request.MonitoringUpdateRequestDTO;
import br.edu.ifba.conectairece.api.features.monitoring.domain.dto.response.MonitoringResponseDTO;
import br.edu.ifba.conectairece.api.features.monitoring.domain.enums.MonitoringStatus;
import br.edu.ifba.conectairece.api.features.monitoring.domain.model.Monitoring;
import br.edu.ifba.conectairece.api.features.monitoring.domain.repository.IMonitoringRepository;
import br.edu.ifba.conectairece.api.features.request.domain.model.Request;
import br.edu.ifba.conectairece.api.features.request.domain.repository.RequestRepository;
import br.edu.ifba.conectairece.api.features.step.domain.model.Step;
import br.edu.ifba.conectairece.api.features.step.domain.repository.IStepRepository;
import br.edu.ifba.conectairece.api.infraestructure.exception.BusinessException;
import br.edu.ifba.conectairece.api.infraestructure.exception.BusinessExceptionMessage;
import br.edu.ifba.conectairece.api.infraestructure.util.ObjectMapperUtil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 *  Service responsible for managing {@link Monitoring} entities.
 *  Handles creation, updating, retrieval, and deletion of request monitorings,
 *  and maps them to their respective DTOs.
 * <p>
 *  Main features:
 *  - Save and update monitorings linked to a request
 *  - Retrieve all monitorings or find by ID
 *  - Delete monitoring by ID
 *
 * @author Giovane Neves
 */
@Service
@RequiredArgsConstructor
public class MonitoringService implements IMonitoringService{

    private final ObjectMapperUtil objectMapperUtil;
    private final RequestRepository requestRepository;
    private final IMonitoringRepository monitoringRepository;
    private final IStepRepository stepRepository;
    private final IFlowStepRepository flowStepRepository;

    @Override
    public MonitoringResponseDTO save(final MonitoringRequestDTO dto) {

        Request request = this.requestRepository.findById(dto.requestId())
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        Step step = this.stepRepository.findById(dto.stepId())
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        Monitoring monitoring = this.objectMapperUtil.map(dto,  Monitoring.class);
        monitoring.setRequest(request);
        monitoring.setStep(step);

        return this.objectMapperUtil.mapToRecord(monitoringRepository.save(monitoring), MonitoringResponseDTO.class);

    }

    @Override
    public MonitoringResponseDTO updateMonitoring(final MonitoringUpdateRequestDTO dto) {

        this.monitoringRepository.findById(dto.id())
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));
        Monitoring monitoring;

        monitoring = this.objectMapperUtil.map(dto,  Monitoring.class);

        return this.objectMapperUtil.mapToRecord(monitoringRepository.save(monitoring), MonitoringResponseDTO.class);

    }

    @Override
    public MonitoringResponseDTO findById(final UUID id) {
        return null;
    }

    @Override
    public List<MonitoringResponseDTO> getAllMonitorings(final Pageable pageable) {

        return this.monitoringRepository.findAll()
                .stream().map(monitoring -> this.objectMapperUtil.mapToRecord(monitoring, MonitoringResponseDTO.class))
                .toList();

    }

    @Override
    public void delete(UUID id) {

    }

    @Transactional
    @Override
    public void completeCurrentMonitoringAndActivateNext(Request request, boolean approved) {

        Monitoring currentMonitoring = this.monitoringRepository
                .findFirstByRequestIdAndStatusOrderByCreatedAtDesc(request.getId(), MonitoringStatus.PENDING)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        // INFO: Update the current status
        currentMonitoring.setMonitoringStatus(approved
                ? MonitoringStatus.COMPLETED
                : MonitoringStatus.REJECTED
        );
        this.monitoringRepository.save(currentMonitoring);

        if(!approved) return;

        // INFO: Search the next flow step
        FlowStep currentStep = this.flowStepRepository.findByFlowIdAndStepId(
                request.getMunicipalService().getFlow().getId(),
                currentMonitoring.getStep().getId()
        ).orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));
        FlowStep nextStep = this.flowStepRepository.findNextStep(currentStep.getFlow().getId(), currentStep.getStepOrder())
                .orElse(null);

        if(nextStep == null) return;

        // INFO: Create a new monitoring
        Monitoring nextMonitoring = new Monitoring();
        nextMonitoring.setStep(nextStep.getStep());
        nextMonitoring.setCode(nextStep.getStep().getCode());
        nextMonitoring.setRequest(request);
        nextMonitoring.setMonitoringStatus(MonitoringStatus.PENDING);

        this.monitoringRepository.save(nextMonitoring);

    }

}
