package br.edu.ifba.conectairece.api.features.update.domain.service;

import br.edu.ifba.conectairece.api.features.update.domain.dto.request.UpdateRequestDTO;
import br.edu.ifba.conectairece.api.features.update.domain.dto.response.UpdateResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Giovane Neves
 */
public interface IUpdateService {

    UpdateResponseDTO save(UpdateRequestDTO updateRequestDTO);
    Page<UpdateResponseDTO> getUpdateList(Pageable pageable);

}
