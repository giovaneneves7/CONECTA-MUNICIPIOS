package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.service;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.dto.request.FluxoRequestDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.atividade.dto.response.AtividadeDadosCompletosResponseDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.atividade.model.Atividade;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.atividade.repository.IAtividadeRepository;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.dto.request.AtividadeFluxoRequestDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.dto.response.FluxoDadosCompletosResponseDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.dto.response.FluxoResponseDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.dto.response.FluxoAtividadeResponseDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.model.Fluxo;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.model.AtividadeFluxo;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.repository.IFluxoRepository;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.repository.IAtividadeFluxoRepository;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servicomunicipal.model.ServicoMunicipal;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servicomunicipal.repository.IServicoMunicipalRepository;
import br.com.cidadesinteligentes.infraestructure.exception.BusinessExceptionMessage;
import br.com.cidadesinteligentes.infraestructure.util.ObjectMapperUtil;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

/**
 * Implementação do serviço para gerenciamento de Fluxos e Atividades.
 * Responsável pela lógica de negócio de criação e recuperação de fluxos de
 * trabalho vinculados a serviços municipais.
 *
 * @author Giovane Neves, Andesson Reis
 */
@Service
@RequiredArgsConstructor
public class FluxoService implements IFluxoService {

    private final ObjectMapperUtil objectMapperUtil;
    private final IFluxoRepository fluxoRepository;
    private final IAtividadeFluxoRepository atividadeFluxoRepository;
    private final IAtividadeRepository atividadeRepository;
    private final IServicoMunicipalRepository servicoMunicipalRepository;

    @Override
    @Transactional
    public FluxoResponseDTO criarFluxo(FluxoRequestDTO dto) {

        ServicoMunicipal servicoMunicipal = this.servicoMunicipalRepository.findById(dto.servicoMunicipalId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        BusinessExceptionMessage.NOT_FOUND.getMessage()));

        // Opcional: Validar se já existe fluxo para este serviço
        Fluxo fluxoExistente = this.fluxoRepository.findByServicoMunicipal(servicoMunicipal);
        if (fluxoExistente != null) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Já existe um fluxo cadastrado para este serviço municipal.");
        }

        Fluxo fluxo = this.objectMapperUtil.map(dto, Fluxo.class);
        fluxo.setServicoMunicipal(servicoMunicipal);

        return this.objectMapperUtil.mapToRecord(this.fluxoRepository.save(fluxo), FluxoResponseDTO.class);
    }

    @Override
    @Transactional
    public FluxoAtividadeResponseDTO criarAtividadeFluxo(AtividadeFluxoRequestDTO dto) {

        Fluxo fluxo = this.fluxoRepository.findById(dto.fluxoId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Fluxo não encontrado."));

        Atividade atividade = this.atividadeRepository.findById(dto.atividadeId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Atividade não encontrada."));

        boolean existeVinculo = this.atividadeFluxoRepository
                .findByFluxoIdAndAtividadeId(dto.fluxoId(), dto.atividadeId())
                .isPresent();

        if (existeVinculo) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Esta atividade já está vinculada a este fluxo.");
        }

        AtividadeFluxo atividadeFluxo = new AtividadeFluxo();
        atividadeFluxo.setFluxo(fluxo);
        atividadeFluxo.setAtividade(atividade);
        atividadeFluxo.setOrdem(dto.ordem());

        return this.objectMapperUtil.mapToRecord(this.atividadeFluxoRepository.save(atividadeFluxo),
                FluxoAtividadeResponseDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FluxoDadosCompletosResponseDTO> buscarTodosFluxos() {

        List<Fluxo> fluxos = fluxoRepository.findAll();

        return fluxos.stream()
                .map(fluxo -> {
                    List<AtividadeDadosCompletosResponseDTO> atividadesDTO = this.buscarAtividadesPorFluxo(fluxo);
                    
                    return new FluxoDadosCompletosResponseDTO(
                            fluxo.getId(),
                            fluxo.getNome(),
                            atividadesDTO,
                            fluxo.getServicoMunicipal().getId());
                })
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public FluxoDadosCompletosResponseDTO buscarFluxoPorId(final UUID id) {

        Fluxo fluxo = this.fluxoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        BusinessExceptionMessage.NOT_FOUND.getMessage()));

        List<AtividadeDadosCompletosResponseDTO> atividadesDTO = this.buscarAtividadesPorFluxo(fluxo);

        return new FluxoDadosCompletosResponseDTO(
                fluxo.getId(),
                fluxo.getNome(),
                atividadesDTO,
                fluxo.getServicoMunicipal().getId());
    }

    @Override
    @Transactional(readOnly = true)
    public FluxoDadosCompletosResponseDTO buscarFluxoPorServicoMunicipalId(Long id) {
        ServicoMunicipal servicoMunicipal = this.servicoMunicipalRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        BusinessExceptionMessage.NOT_FOUND.getMessage()));

        Fluxo fluxo = this.fluxoRepository.findByServicoMunicipal(servicoMunicipal);

        if (fluxo == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Nenhum fluxo encontrado para este serviço municipal.");
        }

        List<AtividadeDadosCompletosResponseDTO> atividadesDTO = this.buscarAtividadesPorFluxo(fluxo);

        return new FluxoDadosCompletosResponseDTO(
                fluxo.getId(),
                fluxo.getNome(),
                atividadesDTO,
                fluxo.getServicoMunicipal().getId());
    }

    @Override
    @Transactional(readOnly = true)
    public AtividadeFluxo buscarPrimeiraAtividadeDoFluxoPorFluxoId(UUID fluxoId) {

        Fluxo fluxo = this.fluxoRepository.findById(fluxoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Fluxo não encontrado."));

        return this.atividadeFluxoRepository.findFirstByFluxoOrderByOrdemAsc(fluxo)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Nenhuma atividade configurada para este fluxo."));
    }

    /**
     * Método auxiliar para buscar e montar o DTO das atividades.
     * Recebe a entidade Fluxo inteira para evitar consultas redundantes ao banco.
     *
     * @param fluxo Entidade Fluxo completa.
     * @return Lista ordenada de atividades.
     */
    private List<AtividadeDadosCompletosResponseDTO> buscarAtividadesPorFluxo(final Fluxo fluxo) {

        List<AtividadeFluxo> vinculos = atividadeFluxoRepository.findAllByFluxoOrderByOrdemAsc(fluxo);

        return vinculos.stream()
                .map(vinculo -> {
                    Atividade atv = vinculo.getAtividade();
                    return new AtividadeDadosCompletosResponseDTO(
                            atv.getId(),
                            atv.getNome(),
                            atv.getCodigo(),
                            atv.getImageUrl(),
                            vinculo.getOrdem());
                })
                .toList();
    }
}