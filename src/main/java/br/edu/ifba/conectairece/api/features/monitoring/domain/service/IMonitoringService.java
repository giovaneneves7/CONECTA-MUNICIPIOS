package br.edu.ifba.conectairece.api.features.monitoring.domain.service;

import br.edu.ifba.conectairece.api.features.monitoring.domain.dto.request.MonitoringRequestDTO;
import br.edu.ifba.conectairece.api.features.monitoring.domain.dto.request.MonitoringUpdateRequestDTO;
import br.edu.ifba.conectairece.api.features.monitoring.domain.dto.response.MonitoringResponseDTO;
import br.edu.ifba.conectairece.api.features.monitoring.domain.model.Monitoring;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

/**
 * @author Giovane Neves
 */
public interface IMonitoringService {

    MonitoringResponseDTO save(final MonitoringRequestDTO dto);
    MonitoringResponseDTO updateMonitoring(final MonitoringUpdateRequestDTO dto);
    MonitoringResponseDTO findById(final UUID id);
    List<MonitoringResponseDTO> getAllMonitorings(Pageable pageable);
    void  delete(final UUID id);
}
