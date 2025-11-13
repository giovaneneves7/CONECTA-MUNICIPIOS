package br.edu.ifba.conectairece.api.features.update.domain.service;

import br.edu.ifba.conectairece.api.features.request.domain.model.Request;
import br.edu.ifba.conectairece.api.features.request.domain.repository.IRequestRepository;
import br.edu.ifba.conectairece.api.features.update.domain.dto.request.UpdateRequestDTO;
import br.edu.ifba.conectairece.api.features.update.domain.dto.response.UpdateResponseDTO;
import br.edu.ifba.conectairece.api.features.update.domain.model.Update;
import br.edu.ifba.conectairece.api.features.update.domain.repository.IUpdateRepository;
import br.edu.ifba.conectairece.api.infraestructure.exception.BusinessException;
import br.edu.ifba.conectairece.api.infraestructure.exception.BusinessExceptionMessage;
import br.edu.ifba.conectairece.api.infraestructure.util.ObjectMapperUtil;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author Giovane Neves
 */
@Service
@RequiredArgsConstructor
public class UpdateService implements IUpdateService {

    private final IUpdateRepository updateRepository;
    private final IRequestRepository requestRepository;
    private final ObjectMapperUtil objectMapperUtil;

    @Override
    public UpdateResponseDTO save(final UpdateRequestDTO updateRequestDTO) {

        Request request = this.requestRepository.findById(updateRequestDTO.requestId())
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        Update update = this.objectMapperUtil.map(updateRequestDTO, Update.class);
        update.setRequest(request);

        return this.objectMapperUtil.mapToRecord(updateRepository.save(update), UpdateResponseDTO.class);

    }

    @Override
    public Page<UpdateResponseDTO> getUpdateList(Pageable pageable)
    {

        return this.updateRepository.findAll(pageable)
                .map(update -> this.objectMapperUtil.mapToRecord(update, UpdateResponseDTO.class));

    }

}
