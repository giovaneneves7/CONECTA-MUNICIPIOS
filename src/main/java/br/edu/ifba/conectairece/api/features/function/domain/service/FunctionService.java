package br.edu.ifba.conectairece.api.features.function.domain.service;

import br.edu.ifba.conectairece.api.features.function.domain.dto.response.FunctionResponseDTO;
import br.edu.ifba.conectairece.api.features.function.domain.model.Function;
import br.edu.ifba.conectairece.api.features.function.domain.repository.FunctionRepository;
import br.edu.ifba.conectairece.api.features.function.domain.repository.projection.FunctionProjection;
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

    private final FunctionRepository functionRepository;

    private final ObjectMapperUtil objectMapperUtil;

    @Override @Transactional
    public FunctionResponseDTO save(Function function) {
        try {
            functionRepository.save(function);
            return objectMapperUtil.map(function, FunctionResponseDTO.class);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error when saving Function");
        }
    }

    @Override @Transactional
    public FunctionResponseDTO update(Function function) {
        try {
            Function existing = this.findById(function.getId());
            existing.setName(function.getName());
            existing.setDescription(function.getDescription());
            functionRepository.save(existing);
            return objectMapperUtil.map(function, FunctionResponseDTO.class);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error when updating Function");
        }
    }

    @Override @Transactional
    public void delete(Long id) {
        try {
            Function function = findById(id);
            functionRepository.delete(function);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error when deleting Function");
        }
    }

    @Override @Transactional(readOnly = true)
    public Page<FunctionProjection> findAllProjectedBy(Pageable pageable) {
        return functionRepository.findAllProjectedBy(pageable);
    }

    @Override @Transactional(readOnly = true)
    public Function findById(Long id) {
        Optional<Function> function = functionRepository.findById(id);
        if (function.isEmpty()) {
            throw new RuntimeException("Function not found");
        }
        return function.get();
    }
}
