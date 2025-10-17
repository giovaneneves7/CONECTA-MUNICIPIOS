package br.edu.ifba.conectairece.api.features.step.domain.service;

import br.edu.ifba.conectairece.api.features.step.domain.dto.response.StepResponseDTO;
import br.edu.ifba.conectairece.api.features.step.domain.model.Step;

/**
 * @author Giovane Neves
 */
public interface IStepService {

    StepResponseDTO createStep(Step step);

}
