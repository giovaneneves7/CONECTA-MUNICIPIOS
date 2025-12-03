package br.com.cidadesinteligentes.modules.marcacaoatendimento.profissionalsaude.service;

import br.com.cidadesinteligentes.infraestructure.exception.BusinessException;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.profissionalsaude.model.ProfissionalSaude;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.profissionalsaude.repository.IProfissionalSaudeRepository;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.servico.model.Servico;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.servico.repository.IServicoRepository;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.tipoprofissionalsaude.model.TipoProfissionalSaude;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.tipoprofissionalsaude.repository.ITipoProfissionalSaudeRepository;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.unidadeatendimento.model.UnidadeAtendimento;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.unidadeatendimento.repository.IUnidadeAtendimentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Serviço responsável pelas regras de negócio de Profissionais de Saúde.
 * Implementa as operações CRUD definidas na interface {@link IProfissionalSaudeService}.
 *
 * <p><b>Regras de Negócio:</b></p>
 * <ul>
 * <li>O CRM deve ser único no sistema.</li>
 * <li>Os serviços oferecidos pelo profissional devem pertencer à Unidade de Atendimento vinculada.</li>
 * <li>Não é possível excluir profissionais que possuam agendamentos vinculados (integridade).</li>
 * </ul>
 *
 * @author Juan Teles Dias
 */
@Service
@RequiredArgsConstructor
public class ProfissionalSaudeService implements IProfissionalSaudeService {

    private final IProfissionalSaudeRepository profissionalRepository;
    private final IUnidadeAtendimentoRepository unidadeRepository;
    private final ITipoProfissionalSaudeRepository tipoRepository;
    private final IServicoRepository servicoRepository;

    @Override
    @Transactional
    public ProfissionalSaude save(ProfissionalSaude entity) {
        // Regra: CRM único
        if (profissionalRepository.existsByCrm(entity.getCrm())) {
            throw new BusinessException("Já existe um profissional cadastrado com este CRM: " + entity.getCrm());
        }

        // Hidrata as referências (Unidade, Tipos, Serviços) que vieram apenas com ID do Controller
        hydrateReferences(entity);

        // Regra: Consistência entre Serviços e Unidade
        validateServicosDaUnidade(entity);

        return profissionalRepository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProfissionalSaude> findAll() {
        return profissionalRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public ProfissionalSaude findById(UUID id) {
        return profissionalRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Profissional de Saúde não encontrado com ID: " + id));
    }

    @Override
    @Transactional
    public ProfissionalSaude update(UUID id, ProfissionalSaude dadosAtualizados) {
        // Reutiliza o metodo padrão findById para buscar a entidade
        ProfissionalSaude entity = this.findById(id);

        // Regra: CRM único na atualização (verificando se mudou e se conflita com outro ID)
        if (!entity.getCrm().equals(dadosAtualizados.getCrm())) {
            if (profissionalRepository.existsByCrmAndIdNot(dadosAtualizados.getCrm(), id)) {
                throw new BusinessException("Já existe outro profissional cadastrado com este CRM.");
            }
        }

        // Atualização dos dados cadastrais simples
        entity.setCrm(dadosAtualizados.getCrm());

        // Para atualizar relacionamentos, primeiro hidratamos os objetos novos (que vieram só com ID)
        hydrateReferences(dadosAtualizados);

        // Substituição das listas/objetos gerenciados
        entity.setUnidadeVinculada(dadosAtualizados.getUnidadeVinculada());
        entity.setTiposProfissional(dadosAtualizados.getTiposProfissional());
        entity.setServicosOferecidos(dadosAtualizados.getServicosOferecidos());

        // Regra: Consistência após alteração
        validateServicosDaUnidade(entity);

        return profissionalRepository.save(entity);
    }

    @Override
    @Transactional
    public ProfissionalSaude delete(UUID id) {
        // Reutiliza o método padrão findById para buscar a entidade
        ProfissionalSaude entity = this.findById(id);

        // Regra: Integridade Referencial (Agendamentos)
        if (entity.getAgendamentos() != null && !entity.getAgendamentos().isEmpty()) {
            throw new BusinessException("Não é possível excluir o profissional pois existem agendamentos vinculados a ele.");
        }

        profissionalRepository.delete(entity);
        return entity;
    }

    // --- Métodos Auxiliares ---

    /**
     * Busca no banco de dados as entidades completas baseadas nos IDs fornecidos no objeto.
     * Garante que todas as referências (Unidade, Tipos, Serviços) existam.
     *
     * @param entity A entidade contendo os objetos "Proxy" (apenas ID).
     */
    private void hydrateReferences(ProfissionalSaude entity) {
        // 1. Unidade de Atendimento
        if (entity.getUnidadeVinculada() != null && entity.getUnidadeVinculada().getId() != null) {
            UnidadeAtendimento unidade = unidadeRepository.findById(entity.getUnidadeVinculada().getId())
                    .orElseThrow(() -> new BusinessException("Unidade de Atendimento não encontrada."));
            entity.setUnidadeVinculada(unidade);
        }

        // 2. Tipos de Profissional (Lista)
        if (entity.getTiposProfissional() != null && !entity.getTiposProfissional().isEmpty()) {
            List<UUID> ids = entity.getTiposProfissional().stream().map(TipoProfissionalSaude::getId).toList();
            List<TipoProfissionalSaude> tiposEncontrados = tipoRepository.findAllById(ids);

            if (tiposEncontrados.size() != ids.size()) {
                throw new BusinessException("Um ou mais Tipos de Profissional informados não foram encontrados no sistema.");
            }
            entity.setTiposProfissional(tiposEncontrados);
        }

        // 3. Serviços Oferecidos (Lista)
        if (entity.getServicosOferecidos() != null && !entity.getServicosOferecidos().isEmpty()) {
            List<UUID> ids = entity.getServicosOferecidos().stream().map(Servico::getId).toList();
            List<Servico> servicosEncontrados = servicoRepository.findAllById(ids);

            if (servicosEncontrados.size() != ids.size()) {
                throw new BusinessException("Um ou mais Serviços informados não foram encontrados no sistema.");
            }
            entity.setServicosOferecidos(servicosEncontrados);
        }
    }

    /**
     * Valida se todos os serviços vinculados ao profissional pertencem à mesma unidade que ele.
     *
     * @param entity O profissional já hidratado.
     */
    private void validateServicosDaUnidade(ProfissionalSaude entity) {
        if (entity.getUnidadeVinculada() == null || entity.getServicosOferecidos() == null) return;

        for (Servico servico : entity.getServicosOferecidos()) {
            // Verifica se o serviço tem unidade e se o ID bate com a do profissional
            if (servico.getUnidade() != null && !servico.getUnidade().getId().equals(entity.getUnidadeVinculada().getId())) {
                throw new BusinessException(
                        String.format("O serviço '%s' pertence a outra unidade. O profissional só pode oferecer serviços da sua unidade de vínculo.", servico.getNome())
                );
            }
        }
    }
}