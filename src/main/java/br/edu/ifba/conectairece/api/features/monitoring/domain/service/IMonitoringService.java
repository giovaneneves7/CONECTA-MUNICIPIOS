package br.edu.ifba.conectairece.api.features.monitoring.domain.service;

import br.edu.ifba.conectairece.api.features.monitoring.domain.dto.request.MonitoringRequestDTO;
import br.edu.ifba.conectairece.api.features.monitoring.domain.dto.response.MonitoringResponseDTO;
import br.edu.ifba.conectairece.api.features.monitoring.domain.model.Monitoring;

import java.util.List;
import java.util.UUID;

/**
 * @author Giovane Neves
 */
public interface IMonitoringService {

    MonitoringResponseDTO save(MonitoringRequestDTO dto);
    MonitoringResponseDTO update(Monitoring monitoring);
    MonitoringResponseDTO findById(UUID id);
    List<MonitoringResponseDTO> findAll();
    void  delete(UUID id);
}
