package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.atualizacao.service;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.atualizacao.dto.request.UpdateRequestDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.atualizacao.dto.response.UpdateResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Giovane Neves
 */
public interface IUpdateService {

    UpdateResponseDTO save(UpdateRequestDTO updateRequestDTO);
    Page<UpdateResponseDTO> getUpdateList(Pageable pageable);

}
