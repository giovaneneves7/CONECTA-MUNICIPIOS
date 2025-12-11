package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.atualizacao.service;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.atualizacao.dto.request.AtualizacaoRequestDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.atualizacao.dto.response.AtualizacaoResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Interface de serviço para gerenciar as operações de negócio referentes às Atualizações.
 *
 * @author Giovane Neves, Andesson Reis
 */
public interface IAtualizacaoService {

    AtualizacaoResponseDTO save(AtualizacaoRequestDTO dto);
    Page<AtualizacaoResponseDTO> findAll(Pageable pageable);

}