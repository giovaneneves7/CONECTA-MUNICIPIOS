package br.edu.ifba.conectairece.api.features.monitoring.domain.service;

import br.edu.ifba.conectairece.api.features.monitoring.domain.dto.request.MonitoringRequestDTO;
import br.edu.ifba.conectairece.api.features.monitoring.domain.dto.response.MonitoringResponseDTO;
import br.edu.ifba.conectairece.api.features.monitoring.domain.model.Monitoring;
import br.edu.ifba.conectairece.api.features.monitoring.domain.repository.IMonitoringRepository;
import br.edu.ifba.conectairece.api.features.request.domain.model.Request;
import br.edu.ifba.conectairece.api.features.request.domain.repository.RequestRepository;
import br.edu.ifba.conectairece.api.features.step.domain.model.Step;
import br.edu.ifba.conectairece.api.features.step.domain.repository.IStepRepository;
import br.edu.ifba.conectairece.api.infraestructure.exception.BusinessException;
import br.edu.ifba.conectairece.api.infraestructure.exception.BusinessExceptionMessage;
import br.edu.ifba.conectairece.api.infraestructure.util.ObjectMapperUtil;

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
    public MonitoringResponseDTO update(final Monitoring monitoring) {
        return null;
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
}
