package br.edu.ifba.conectairece.api.features.function.domain.service;

import br.edu.ifba.conectairece.api.features.function.domain.dto.response.FunctionResponseDTO;
import br.edu.ifba.conectairece.api.features.function.domain.model.Function;
import br.edu.ifba.conectairece.api.features.function.domain.repository.IFunctionRepository;
import br.edu.ifba.conectairece.api.features.function.domain.repository.projection.FunctionProjection;
import br.edu.ifba.conectairece.api.infraestructure.exception.BusinessException;
import br.edu.ifba.conectairece.api.infraestructure.exception.BusinessExceptionMessage;
import br.edu.ifba.conectairece.api.infraestructure.util.ObjectMapperUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


/**
 * Service class responsible for handling business logic related to {@link Function}.
 *
 * @author Jorge Roberto
 */
@RequiredArgsConstructor
@Service
public class FunctionService implements FunctionIService{

    private final IFunctionRepository functionRepository;

    private final ObjectMapperUtil objectMapperUtil;

    @Override @Transactional
    public FunctionResponseDTO save(Function function) {
        if (functionRepository.findByName(function.getName()).isPresent()) {
            throw new BusinessException(BusinessExceptionMessage.ATTRIBUTE_VALUE_ALREADY_EXISTS.getMessage());
        }
        functionRepository.save(function);
        return objectMapperUtil.map(function, FunctionResponseDTO.class);
    }

    @Override @Transactional
    public FunctionResponseDTO update(Function function) {
        Function existing = this.findById(function.getId());

        Optional<Function> existingFunctionWithSameName = functionRepository.findByNameAndIdNot(function.getName(), existing.getId());
        if (existingFunctionWithSameName.isPresent()) {
            throw new BusinessException(BusinessExceptionMessage.ATTRIBUTE_VALUE_ALREADY_EXISTS.getMessage());
        }

        existing.setName(function.getName());
        existing.setDescription(function.getDescription());

        functionRepository.save(existing);
        return objectMapperUtil.map(existing, FunctionResponseDTO.class);
    }

    @Override @Transactional
    public void delete(Long id) {
        Function function = findById(id);

        if (!function.getPublicServantProfiles().isEmpty()) {
            throw new BusinessException(BusinessExceptionMessage.CLASS_IN_USE.getMessage());
        }

        functionRepository.delete(function);
    }

    @Override @Transactional(readOnly = true)
    public Page<FunctionProjection> findAllProjectedBy(Pageable pageable) {
        return functionRepository.findAllProjectedBy(pageable);
    }

    @Override @Transactional(readOnly = true)
    public Function findById(Long id) {
        Optional<Function> function = functionRepository.findById(id);
        if (function.isEmpty()) {
            throw new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage());
        }
        return function.get();
    }
}
