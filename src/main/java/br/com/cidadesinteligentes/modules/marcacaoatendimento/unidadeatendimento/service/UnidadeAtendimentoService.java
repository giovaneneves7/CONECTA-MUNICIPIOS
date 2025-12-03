package br.com.cidadesinteligentes.modules.marcacaoatendimento.unidadeatendimento.service;

import br.com.cidadesinteligentes.infraestructure.exception.BusinessException;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.unidadeatendimento.model.UnidadeAtendimento;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.unidadeatendimento.repository.IUnidadeAtendimentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Serviço responsável pelas regras de negócio de Unidades de Atendimento.
 * Implementa as operações CRUD definidas na interface {@link IUnidadeAtendimentoService}.
 *
 * @author Juan Teles Dias
 */
@Service
@RequiredArgsConstructor
public class UnidadeAtendimentoService implements IUnidadeAtendimentoService {

    private final IUnidadeAtendimentoRepository repository;

    @Override
    @Transactional
    public UnidadeAtendimento save(UnidadeAtendimento unidadeAtendimento) {
        // Regra de Negócio: Validação de duplicidade de CNES
        if (repository.existsByCodigoCNES(unidadeAtendimento.getCodigoCNES())) {
            throw new BusinessException("Já existe uma unidade cadastrada com o CNES informado: " + unidadeAtendimento.getCodigoCNES());
        }

        return repository.save(unidadeAtendimento);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UnidadeAtendimento> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public UnidadeAtendimento findById(UUID id) {
        return findEntityById(id);
    }

    @Override
    @Transactional
    public UnidadeAtendimento update(UUID id, UnidadeAtendimento dadosAtualizados) {
        UnidadeAtendimento entity = findEntityById(id);

        // Regra de Negócio: Se o CNES foi alterado, verificar se o novo já existe em OUTRA unidade
        if (!entity.getCodigoCNES().equals(dadosAtualizados.getCodigoCNES())) {
            boolean cnesJaExiste = repository.existsByCodigoCNESAndIdNot(dadosAtualizados.getCodigoCNES(), id);
            if (cnesJaExiste) {
                throw new BusinessException("Já existe outra unidade cadastrada com o CNES informado: " + dadosAtualizados.getCodigoCNES());
            }
        }

        // Atualização dos dados cadastrais
        entity.setNome(dadosAtualizados.getNome());
        entity.setCodigoCNES(dadosAtualizados.getCodigoCNES());
        entity.setEndereco(dadosAtualizados.getEndereco());
        entity.setTelefone(dadosAtualizados.getTelefone());
        entity.setHorarioFuncionamento(dadosAtualizados.getHorarioFuncionamento());

        // Nota: O tipo da unidade (discriminator) não é alterado após a criação.

        return repository.save(entity);
    }

    @Override
    @Transactional
    public UnidadeAtendimento delete(UUID id) {
        UnidadeAtendimento entity = findEntityById(id);

        // Regra de Negócio: Validação de integridade antes da exclusão
        // Verifica se a unidade possui serviços ofertados vinculados a ela
        if (entity.getServicosOferecidos() != null && !entity.getServicosOferecidos().isEmpty()) {
            throw new BusinessException("Não é possível excluir a unidade pois existem serviços vinculados a ela.");
        }

        repository.delete(entity);
        return entity;
    }

    // --- Métodos Auxiliares ---

    private UnidadeAtendimento findEntityById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new BusinessException("Unidade de Atendimento não encontrada com ID: " + id));
    }
}