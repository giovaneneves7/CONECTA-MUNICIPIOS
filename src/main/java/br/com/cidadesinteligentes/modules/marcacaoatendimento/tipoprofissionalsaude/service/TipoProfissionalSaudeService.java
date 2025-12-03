package br.com.cidadesinteligentes.modules.marcacaoatendimento.tipoprofissionalsaude.service;

import br.com.cidadesinteligentes.infraestructure.exception.BusinessException;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.tipoprofissionalsaude.model.TipoProfissionalSaude;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.tipoprofissionalsaude.repository.ITipoProfissionalSaudeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Serviço responsável pelas regras de negócio de Tipos de Profissional.
 *
 * @author Juan Teles Dias
 */
@Service
@RequiredArgsConstructor
public class TipoProfissionalSaudeService implements ITipoProfissionalSaudeService {

    private final ITipoProfissionalSaudeRepository repository;

    @Override
    @Transactional
    public TipoProfissionalSaude save(TipoProfissionalSaude entity) {
        // Regra: Não permitir nomes duplicados (Ex: Não ter dois "Médico")
        if (repository.existsByNomeIgnoreCase(entity.getNome())) {
            throw new BusinessException("Já existe um Tipo de Profissional cadastrado com este nome: " + entity.getNome());
        }
        return repository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoProfissionalSaude> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public TipoProfissionalSaude findById(UUID id) {
        return findEntityById(id);
    }

    @Override
    @Transactional
    public TipoProfissionalSaude update(UUID id, TipoProfissionalSaude dadosAtualizados) {
        TipoProfissionalSaude entity = findEntityById(id);

        // Regra: Validar duplicidade de nome na atualização (exceto o próprio)
        if (!entity.getNome().equalsIgnoreCase(dadosAtualizados.getNome())) {
            if (repository.existsByNomeIgnoreCaseAndIdNot(dadosAtualizados.getNome(), id)) {
                throw new BusinessException("Já existe outro Tipo de Profissional com este nome.");
            }
        }

        entity.setNome(dadosAtualizados.getNome());
        entity.setDescricao(dadosAtualizados.getDescricao());

        return repository.save(entity);
    }

    @Override
    @Transactional
    public TipoProfissionalSaude delete(UUID id) {
        TipoProfissionalSaude entity = findEntityById(id);

        // Regra: Integridade Referencial
        if (entity.getProfissionais() != null && !entity.getProfissionais().isEmpty()) {
            throw new BusinessException("Não é possível excluir este Tipo pois existem profissionais vinculados a ele.");
        }

        repository.delete(entity);
        return entity;
    }

    private TipoProfissionalSaude findEntityById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new BusinessException("Tipo de Profissional não encontrado com ID: " + id));
    }
}