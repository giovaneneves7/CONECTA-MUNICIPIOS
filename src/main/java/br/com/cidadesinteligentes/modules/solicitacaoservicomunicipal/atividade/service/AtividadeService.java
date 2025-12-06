package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.atividade.service;

import br.com.cidadesinteligentes.infraestructure.util.ObjectMapperUtil;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.atividade.dto.response.AtividadeResponseDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.atividade.model.Atividade;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.atividade.repository.IAtividadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Giovane Neves, Andesson Reis
 */

@Service
@RequiredArgsConstructor
public class AtividadeService implements IAtividadeService {

    private final ObjectMapperUtil objectMapperUtil;
    private final IAtividadeRepository atividadeRepository;

    @Override
    public AtividadeResponseDTO save(final Atividade atividade) {

        return this.objectMapperUtil.mapToRecord(this.atividadeRepository.save(atividade), AtividadeResponseDTO.class);

    }

    @Override
    public List<AtividadeResponseDTO> findAll(Pageable pageable) {

        return this.atividadeRepository.findAll(pageable)
                .stream()
                .map(atividade -> this.objectMapperUtil.mapToRecord(atividade, AtividadeResponseDTO.class))
                .toList();

    }
}
