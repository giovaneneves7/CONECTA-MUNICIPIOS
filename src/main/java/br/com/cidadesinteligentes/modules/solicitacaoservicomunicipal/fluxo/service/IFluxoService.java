package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.service;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.dto.request.EtapaFluxoRequestDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.dto.request.FluxoRequestDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.dto.response.FluxoDadosCompletosResponseDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.dto.response.FluxoEtapaResponseDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.dto.response.FluxoResponseDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.model.EtapaFluxo;

import java.util.List;
import java.util.UUID;

/**
 * @author Giovane Neves, Andesson Reis
 */
public interface IFluxoService {

    FluxoResponseDTO criarFluxo(final FluxoRequestDTO dto);
    FluxoEtapaResponseDTO criarEtapaFluxo(final EtapaFluxoRequestDTO dto);
    List<FluxoDadosCompletosResponseDTO> buscarTodosFluxos();
    FluxoDadosCompletosResponseDTO buscarFluxoPorId(final UUID id);
    FluxoDadosCompletosResponseDTO buscarFluxoPorServicoMunicipalId(final Long id);
    EtapaFluxo buscarPrimeiraEtapaFluxoPorFluxoId(final UUID flowId);
}
