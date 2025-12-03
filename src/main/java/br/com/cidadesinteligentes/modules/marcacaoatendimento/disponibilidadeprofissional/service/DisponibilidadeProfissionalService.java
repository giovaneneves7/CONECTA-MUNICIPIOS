package br.com.cidadesinteligentes.modules.marcacaoatendimento.disponibilidadeprofissional.service;

import br.com.cidadesinteligentes.infraestructure.exception.BusinessException;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.disponibilidadeprofissional.model.DisponibilidadeProfissional;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.disponibilidadeprofissional.repository.IDisponibilidadeProfissionalRepository;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.profissionalsaude.model.ProfissionalSaude;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.profissionalsaude.repository.IProfissionalSaudeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Serviço responsável pelas regras de negócio da Grade de Horários.
 *
 * <p><b>Regras de Negócio:</b></p>
 * <ul>
 * <li>A hora final deve ser posterior à hora inicial.</li>
 * <li>Não é permitido cadastrar horários sobrepostos para o mesmo profissional no mesmo dia.</li>
 * </ul>
 *
 * @author Juan Teles Dias
 */
@Service
@RequiredArgsConstructor
public class DisponibilidadeProfissionalService implements IDisponibilidadeProfissionalService {

    private final IDisponibilidadeProfissionalRepository repository;
    private final IProfissionalSaudeRepository profissionalRepository;

    @Override
    @Transactional
    public DisponibilidadeProfissional save(DisponibilidadeProfissional entity) {
        // Regra: Hora Fim > Hora Inicio
        if (!entity.getHoraFim().isAfter(entity.getHoraInicio())) {
            throw new BusinessException("A hora final deve ser posterior à hora inicial.");
        }

        // Hidratação do Profissional
        if (entity.getProfissional() != null && entity.getProfissional().getId() != null) {
            ProfissionalSaude profissional = profissionalRepository.findById(entity.getProfissional().getId())
                    .orElseThrow(() -> new BusinessException("Profissional não encontrado."));
            entity.setProfissional(profissional);
        } else {
            throw new BusinessException("O Profissional é obrigatório.");
        }

        // Regra: Não permitir sobreposição de horário (Overlap)
        boolean existeConflito = repository.existsOverlapping(
                entity.getProfissional(),
                entity.getDia(),
                entity.getHoraInicio(),
                entity.getHoraFim()
        );

        if (existeConflito) {
            throw new BusinessException("Já existe uma disponibilidade cadastrada que conflita com este horário.");
        }

        return repository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DisponibilidadeProfissional> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public DisponibilidadeProfissional findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new BusinessException("Disponibilidade não encontrada com ID: " + id));
    }

    @Override
    @Transactional
    public DisponibilidadeProfissional delete(UUID id) {
        DisponibilidadeProfissional entity = findById(id);
        repository.delete(entity);
        return entity;
    }
}