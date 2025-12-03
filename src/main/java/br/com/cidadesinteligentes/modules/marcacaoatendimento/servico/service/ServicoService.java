package br.com.cidadesinteligentes.modules.marcacaoatendimento.servico.service;

import br.com.cidadesinteligentes.infraestructure.exception.BusinessException;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.servico.model.Servico;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.servico.repository.IServicoRepository;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.unidadeatendimento.model.UnidadeAtendimento;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.unidadeatendimento.repository.IUnidadeAtendimentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Serviço responsável pelas regras de negócio de Serviços.
 * Implementa as operações CRUD definidas na interface {@link IServicoService}.
 *
 * <p><b>Regras de Negócio:</b></p>
 * <ul>
 * <li>Um serviço deve estar sempre vinculado a uma Unidade de Atendimento existente.</li>
 * <li>Não é permitido duplicar serviços com o mesmo nome na mesma unidade (validável via banco ou aqui).</li>
 * </ul>
 *
 * @author Juan Teles Dias
 */
@Service
@RequiredArgsConstructor
public class ServicoService implements IServicoService {

    private final IServicoRepository servicoRepository;
    private final IUnidadeAtendimentoRepository unidadeAtendimentoRepository;

    /**
     * Salva um novo serviço, validando a existência da Unidade de Atendimento vinculada.
     */
    @Override
    @Transactional
    public Servico save(Servico servico) {
        // Validação e Hidratação da Unidade
        if (servico.getUnidade() == null || servico.getUnidade().getId() == null) {
            throw new BusinessException("A Unidade de Atendimento é obrigatória.");
        }

        UnidadeAtendimento unidade = unidadeAtendimentoRepository.findById(servico.getUnidade().getId())
                .orElseThrow(() -> new BusinessException("Unidade de Atendimento não encontrada."));

        servico.setUnidade(unidade);

        return servicoRepository.save(servico);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Servico> findAll() {
        return servicoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Servico findById(UUID id) {
        return findServicoEntityById(id);
    }

    /**
     * Atualiza um serviço. Permite alterar o nome ou mover o serviço de unidade.
     */
    @Override
    @Transactional
    public Servico update(UUID id, Servico dadosAtualizados) {
        Servico servicoAtual = findServicoEntityById(id);

        // Atualiza o nome
        servicoAtual.setNome(dadosAtualizados.getNome());

        // Se houve alteração de unidade, valida a nova unidade
        if (dadosAtualizados.getUnidade() != null && dadosAtualizados.getUnidade().getId() != null) {
            if (!dadosAtualizados.getUnidade().getId().equals(servicoAtual.getUnidade().getId())) {
                UnidadeAtendimento novaUnidade = unidadeAtendimentoRepository.findById(dadosAtualizados.getUnidade().getId())
                        .orElseThrow(() -> new BusinessException("Nova Unidade de Atendimento não encontrada."));
                servicoAtual.setUnidade(novaUnidade);
            }
        }

        return servicoRepository.save(servicoAtual);
    }

    /**
     * Realiza a exclusão física do serviço.
     * <p>Nota: Pode falhar se houver integridade referencial (agendamentos vinculados).</p>
     */
    @Override
    @Transactional
    public Servico delete(UUID id) {
        Servico servico = findServicoEntityById(id);

        // Aqui poderia haver uma validação: se tiver agendamentos, impedir exclusão ou fazer soft-delete.
        // Como o modelo não tem campo de status, faremos a exclusão padrão do JPA.

        servicoRepository.delete(servico);
        return servico;
    }

    // --- Métodos Auxiliares ---

    private Servico findServicoEntityById(UUID id) {
        return servicoRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Serviço não encontrado com ID: " + id));
    }
}