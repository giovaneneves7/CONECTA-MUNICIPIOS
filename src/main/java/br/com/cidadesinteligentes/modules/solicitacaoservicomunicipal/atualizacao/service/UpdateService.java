package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.atualizacao.service;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.model.Request;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.repository.IRequestRepository;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.atualizacao.dto.request.UpdateRequestDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.atualizacao.dto.response.UpdateResponseDTO;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.atualizacao.model.Update;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.atualizacao.repository.IUpdateRepository;
import br.com.cidadesinteligentes.infraestructure.exception.BusinessException;
import br.com.cidadesinteligentes.infraestructure.exception.BusinessExceptionMessage;
import br.com.cidadesinteligentes.infraestructure.util.ObjectMapperUtil;

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
