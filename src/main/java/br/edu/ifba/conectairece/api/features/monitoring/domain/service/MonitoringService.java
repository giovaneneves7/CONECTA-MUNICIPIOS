package br.edu.ifba.conectairece.api.features.monitoring.domain.service;

import br.edu.ifba.conectairece.api.features.monitoring.domain.dto.request.MonitoringRequestDTO;
import br.edu.ifba.conectairece.api.features.monitoring.domain.dto.response.MonitoringResponseDTO;
import br.edu.ifba.conectairece.api.features.monitoring.domain.model.Monitoring;
import br.edu.ifba.conectairece.api.features.monitoring.domain.repository.IMonitoringRepository;
import br.edu.ifba.conectairece.api.features.request.domain.model.Request;
import br.edu.ifba.conectairece.api.features.request.domain.repository.RequestRepository;
import br.edu.ifba.conectairece.api.infraestructure.exception.BusinessException;
import br.edu.ifba.conectairece.api.infraestructure.exception.BusinessExceptionMessage;
import br.edu.ifba.conectairece.api.infraestructure.util.ObjectMapperUtil;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author Giovane Neves
 */
@Service
@RequiredArgsConstructor
public class MonitoringService implements IMonitoringService{

    @Autowired
    private final ObjectMapperUtil objectMapperUtil;
    @Autowired
    private final RequestRepository requestRepository;
    @Autowired
    private final IMonitoringRepository monitoringRepository;

    @Override
    public MonitoringResponseDTO save(MonitoringRequestDTO dto) {

        Request request = this.requestRepository.findById(dto.requestId())
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));
        Monitoring monitoring = this.objectMapperUtil.map(dto,  Monitoring.class);
        monitoring.setRequest(request);

        return this.objectMapperUtil.mapToRecord(monitoringRepository.save(monitoring), MonitoringResponseDTO.class);

    }

    @Override
    public MonitoringResponseDTO update(Monitoring monitoring) {
        return null;
    }

    @Override
    public MonitoringResponseDTO findById(UUID id) {
        return null;
    }

    @Override
    public List<MonitoringResponseDTO> findAll() {
        return List.of();
    }

    @Override
    public void delete(UUID id) {

    }
}
