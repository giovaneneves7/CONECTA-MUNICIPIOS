package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.service;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.dto.request.FluxoRequestDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.dto.request.EtapaFluxoRequestDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.dto.response.FluxoDadosCompletosResponseDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.dto.response.FluxoResponseDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.dto.response.FluxoEtapaResponseDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.model.Fluxo;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.model.EtapaFluxo;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.repository.IFluxoRepository;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.repository.IEtapaFluxoRepository;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servicomunicipal.model.ServicoMunicipal;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servicomunicipal.repository.IServicoMunicipalRepository;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.etapa.dto.response.EtapaDadosCompletosResponseDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.etapa.model.Etapa;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.etapa.repository.IEtapaRepository;
import br.com.cidadesinteligentes.infraestructure.exception.BusinessException;
import br.com.cidadesinteligentes.infraestructure.exception.BusinessExceptionMessage;
import br.com.cidadesinteligentes.infraestructure.util.ObjectMapperUtil;
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
public class FlowService implements IFluxoService {

    private final ObjectMapperUtil objectMapperUtil;
    private final IFluxoRepository fluxoRepository;
    private final IEtapaFluxoRepository etapaFluxoRepository;
    private final IEtapaRepository etapaRepository;
    private final IServicoMunicipalRepository servicoMunicipalRepository;

    @Override
    public FluxoResponseDTO criarFluxo(FluxoRequestDTO dto) {

        ServicoMunicipal servicoMunicipal = this.servicoMunicipalRepository.findById(dto.servicoMunicipalId())
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        Fluxo fluxo = this.objectMapperUtil.map(dto, Fluxo.class);
        fluxo.setServicoMunicipal(servicoMunicipal);

        return this.objectMapperUtil.mapToRecord(this.fluxoRepository.save(fluxo),  FluxoResponseDTO.class);

    }

    @Override
    public FluxoEtapaResponseDTO criarEtapaFluxo(EtapaFluxoRequestDTO dto) {

        Fluxo fluxo = this.fluxoRepository.findById(dto.fluxoId())
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        Etapa etapa = this.etapaRepository.findById(dto.etapaId())
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        EtapaFluxo etapaFluxo = new EtapaFluxo();
        etapaFluxo.setFluxo(fluxo);
        etapaFluxo.setEtapa(etapa);
        etapaFluxo.setOrdemEtapa(dto.ordemEtapa());

        return this.objectMapperUtil.mapToRecord(this.etapaFluxoRepository.save(etapaFluxo), FluxoEtapaResponseDTO.class);

    }

    @Override
    public List<FluxoDadosCompletosResponseDTO> buscarTodosFluxos() {

        List<Fluxo> fluxos = fluxoRepository.findAll();
        List<FluxoDadosCompletosResponseDTO> responseList = new ArrayList<>();

        for(Fluxo fluxo : fluxos) {

            List<EtapaDadosCompletosResponseDTO> etapaDTOs = this.findStepsByFlowId(fluxo.getId());

            FluxoDadosCompletosResponseDTO fluxoDTO = new FluxoDadosCompletosResponseDTO(
                    fluxo.getId(),
                    fluxo.getNome(),
                    etapaDTOs,
                    fluxo.getServicoMunicipal().getId()
            );

            responseList.add(fluxoDTO);

        }
        return responseList;

    }

    @Override
    public FluxoDadosCompletosResponseDTO buscarFluxoPorId(final UUID id) {

        Fluxo fluxo = this.fluxoRepository.findById(id)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        List<EtapaDadosCompletosResponseDTO> etapaDTOs = this.findStepsByFlowId(fluxo.getId());

        return new FluxoDadosCompletosResponseDTO(
                fluxo.getId(),
                fluxo.getNome(),
                etapaDTOs,
                fluxo.getServicoMunicipal().getId()
        );

    }

        @Override
        public FluxoDadosCompletosResponseDTO buscarFluxoPorServicoMunicipalId(Long id) {

        ServicoMunicipal servicoMunicipal = this.servicoMunicipalRepository.findById(id)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        Fluxo fluxo = this.fluxoRepository.findByServicoMunicipal(servicoMunicipal);

        if (fluxo == null) {
                throw new BusinessException("Nenhum fluxo encontrado para este serviço municipal.");
        }

        List<EtapaDadosCompletosResponseDTO> etapaDTOs = this.findStepsByFlowId(fluxo.getId());

        return new FluxoDadosCompletosResponseDTO(
                fluxo.getId(),
                fluxo.getNome(),
                etapaDTOs,
                fluxo.getServicoMunicipal().getId()
        );

        }


    @Override
    public EtapaFluxo buscarPrimeiraEtapaFluxoPorFluxoId(UUID fluxoId) {

        Fluxo fluxo = this.fluxoRepository.findById(fluxoId)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        return this.etapaFluxoRepository.findFirstByFluxoOrderByOrdemEtapaAsc(fluxo)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

    }

    private List<EtapaDadosCompletosResponseDTO> findStepsByFlowId(final UUID flowId){
        List<Etapa> etapas = etapaRepository.findAllByFlowId(flowId);

        return etapas.stream()
                .map(etapa -> {

                    EtapaFluxo etapaFluxo = etapaFluxoRepository.findByFluxoIdAndEtapaId(flowId, etapa.getId())
                            .orElse(null);
                    long ordem = (etapaFluxo != null) ? etapaFluxo.getOrdemEtapa() : 0;

                    return new EtapaDadosCompletosResponseDTO(
                            etapa.getId(),
                            etapa.getNome(),
                            etapa.getCode(),
                            etapa.getImageUrl(),
                            ordem
                    );
                })
                .sorted(Comparator.comparingLong(EtapaDadosCompletosResponseDTO::ordem))
                .toList();
    }

}