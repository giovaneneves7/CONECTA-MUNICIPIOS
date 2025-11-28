package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.acompanhamento.service;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.acompanhamento.dto.request.MonitoringRequestDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.acompanhamento.dto.request.MonitoringUpdateRequestDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.acompanhamento.dto.response.MonitoringResponseDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.model.Request;
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
    void completeCurrentMonitoringAndActivateNext(final Request request, boolean approved);

}
