package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.acompanhamento.service;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.model.EtapaFluxo;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.repository.IEtapaFluxoRepository;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.acompanhamento.dto.request.MonitoringRequestDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.acompanhamento.dto.request.MonitoringUpdateRequestDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.acompanhamento.dto.response.MonitoringResponseDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.acompanhamento.enums.MonitoringStatus;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.acompanhamento.model.Monitoring;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.acompanhamento.repository.IMonitoringRepository;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.model.Request;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.repository.IRequestRepository;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.etapa.model.Etapa;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.etapa.repository.IEtapaRepository;
import br.com.cidadesinteligentes.infraestructure.exception.BusinessException;
import br.com.cidadesinteligentes.infraestructure.exception.BusinessExceptionMessage;
import br.com.cidadesinteligentes.infraestructure.util.ObjectMapperUtil;

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
    private final IRequestRepository requestRepository;
    private final IMonitoringRepository monitoringRepository;
    private final IEtapaRepository stepRepository;
    private final IEtapaFluxoRepository flowStepRepository;

    @Override
    public MonitoringResponseDTO save(final MonitoringRequestDTO dto) {

        Request request = this.requestRepository.findById(dto.requestId())
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        Etapa step = this.stepRepository.findById(dto.stepId())
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        Monitoring monitoring = this.objectMapperUtil.map(dto,  Monitoring.class);
        monitoring.setRequest(request);
        monitoring.setEtapa(step);

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
        EtapaFluxo currentStep = this.flowStepRepository.findByFluxoIdAndEtapaId(
                request.getServicoMunicipal().getFluxo().getId(),
                currentMonitoring.getEtapa().getId()
        ).orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));
        EtapaFluxo nextStep = this.flowStepRepository.buscarProximaEtapa(currentStep.getFluxo().getId(), currentStep.getOrdemEtapa())
                .orElse(null);

        if(nextStep == null) return;

        // INFO: Create a new monitoring
        Monitoring nextMonitoring = new Monitoring();
        nextMonitoring.setEtapa(nextStep.getEtapa());
        nextMonitoring.setCode(nextStep.getEtapa().getCode());
        nextMonitoring.setRequest(request);
        nextMonitoring.setMonitoringStatus(MonitoringStatus.PENDING);

        this.monitoringRepository.save(nextMonitoring);

    }

}
