package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.atividade.service;

import org.springframework.data.domain.Pageable;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.atividade.dto.response.AtividadeResponseDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.atividade.model.Atividade;

import java.util.List;

/**
 * @author Giovane Neves, Andesson Reis
 */
public interface IAtividadeService {

    AtividadeResponseDTO save (Atividade atividade);

    List<AtividadeResponseDTO> findAll(Pageable pageable);

}
