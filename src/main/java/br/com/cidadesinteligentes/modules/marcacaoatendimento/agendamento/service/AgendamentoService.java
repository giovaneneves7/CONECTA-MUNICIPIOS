package br.com.cidadesinteligentes.modules.marcacaoatendimento.agendamento.service;

import br.com.cidadesinteligentes.infraestructure.exception.BusinessException;
import br.com.cidadesinteligentes.modules.core.gestaousuario.cidadao.model.Citizen;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.agendamento.enums.StatusAgendamento;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.agendamento.model.Agendamento;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.agendamento.repository.IAgendamentoRepository;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.disponibilidadeprofissional.enums.DiaSemana;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.disponibilidadeprofissional.repository.IDisponibilidadeProfissionalRepository;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.profissionalsaude.model.ProfissionalSaude;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.profissionalsaude.repository.IProfissionalSaudeRepository;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.servico.model.Servico;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.servico.repository.IServicoRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

/**
 * Serviço responsável pelas regras de negócio de Agendamento.
 * Implementa operações CRUD padrão (save, update, delete, find) operando sobre a Entidade.
 *
 * @author Juan Teles Dias
 */
@Service
@RequiredArgsConstructor
public class AgendamentoService implements IAgendamentoService {

    private final IAgendamentoRepository agendamentoRepository;
    private final IProfissionalSaudeRepository profissionalSaudeRepository;
    private final IServicoRepository servicoRepository;
    private final IDisponibilidadeProfissionalRepository disponibilidadeRepository;

    // Injetamos o EntityManager para gerenciar referências sem precisar de Repository externo
    private final EntityManager entityManager;

    /**
     * Salva (cria) um novo agendamento.
     * Recebe uma entidade parcialmente preenchida (com IDs nas referências) e a persiste após validação.
     */
    @Override
    @Transactional
    public Agendamento save(Agendamento agendamento) {

        // Regra: Data futura
        validateDateInFuture(agendamento.getData(), agendamento.getHora());

        // "Hidratação" das Entidades Relacionadas:
        if (agendamento.getProfissional() == null || agendamento.getProfissional().getId() == null) {
            throw new BusinessException("O Profissional de Saúde é obrigatório.");
        }
        ProfissionalSaude profissional = profissionalSaudeRepository.findById(agendamento.getProfissional().getId())
                .orElseThrow(() -> new BusinessException("Profissional de Saúde não encontrado."));

        if (agendamento.getServico() == null || agendamento.getServico().getId() == null) {
            throw new BusinessException("O Serviço é obrigatório.");
        }
        Servico servico = servicoRepository.findById(agendamento.getServico().getId())
                .orElseThrow(() -> new BusinessException("Serviço não encontrado."));

        if (agendamento.getPaciente() == null || agendamento.getPaciente().getId() == null) {
            throw new BusinessException("O Paciente é obrigatório.");
        }

        // --- SOLUÇÃO SEM REPOSITORY DE CIDADÃO ---
        // Utilizamos getReference para criar um proxy apenas com o ID.
        // O Hibernate usará esse ID para preencher a FK 'paciente_id' ao salvar o agendamento.
        // Nota: Se o ID não existir no banco, ocorrerá um erro de ConstraintViolation no momento do commit.
        Citizen paciente = entityManager.getReference(Citizen.class, agendamento.getPaciente().getId());

        // Regra: Validar se médico atende nesse dia da semana e horário (Grade de Trabalho)
        existsByProfissionalAndDataAndHoraAndStatusNot(profissional, agendamento.getData(), agendamento.getHora());

        // Regra: Verificar colisão (se já existe agendamento neste horário)
        boolean horarioOcupado = agendamentoRepository.existsByProfissionalAndDataAndHoraAndStatusNot(
                profissional, agendamento.getData(), agendamento.getHora(), StatusAgendamento.CANCELADO);

        if (horarioOcupado) {
            throw new BusinessException("Horário já ocupado para este profissional.");
        }

        // Atualiza a entidade com os objetos gerenciados
        agendamento.setProfissional(profissional);
        agendamento.setServico(servico);
        agendamento.setPaciente(paciente);
        agendamento.setStatus(StatusAgendamento.AGENDADO);

        return agendamentoRepository.save(agendamento);
    }

    @Override
    @Transactional(readOnly = true)
    public Agendamento findById(UUID id) {
        return findAgendamentoEntityById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Agendamento> findAll() {
        return agendamentoRepository.findAll();
    }

    /**
     * Atualiza (reagenda) um agendamento existente.
     * Recebe uma entidade com os dados atualizados (data/hora).
     */
    @Override
    @Transactional
    public Agendamento update(UUID id, Agendamento dadosAtualizados) {
        Agendamento agendamentoAtual = findAgendamentoEntityById(id);

        validateDateInFuture(dadosAtualizados.getData(), dadosAtualizados.getHora());

        // Regra: Validar grade do profissional para a NOVA data
        existsByProfissionalAndDataAndHoraAndStatusNot(agendamentoAtual.getProfissional(), dadosAtualizados.getData(), dadosAtualizados.getHora());

        // Verifica se o NOVO horário está ocupado.
        boolean horarioOcupado = agendamentoRepository.existsByProfissionalAndDataAndHoraAndStatusNot(
                agendamentoAtual.getProfissional(),
                dadosAtualizados.getData(),
                dadosAtualizados.getHora(),
                StatusAgendamento.CANCELADO);

        if (horarioOcupado) {
            // Lógica: se o horário ocupado for diferente do atual, é conflito.
            if (!agendamentoAtual.getData().isEqual(dadosAtualizados.getData()) || !agendamentoAtual.getHora().equals(dadosAtualizados.getHora())) {
                throw new BusinessException("O profissional já possui um agendamento neste novo horário.");
            }
        }

        // Atualiza apenas os campos permitidos
        agendamentoAtual.setData(dadosAtualizados.getData());
        agendamentoAtual.setHora(dadosAtualizados.getHora());
        agendamentoAtual.setStatus(StatusAgendamento.REAGENDADO);

        return agendamentoRepository.save(agendamentoAtual);
    }

    @Override
    @Transactional
    public Agendamento updateStatus(UUID id, StatusAgendamento novoStatus) {
        Agendamento agendamento = findAgendamentoEntityById(id);

        if (agendamento.getStatus() == StatusAgendamento.CANCELADO) {
            throw new IllegalStateException("Não é possível alterar o status de um agendamento cancelado.");
        }

        agendamento.setStatus(novoStatus);
        return agendamentoRepository.save(agendamento);
    }

    @Override
    @Transactional
    public Agendamento delete(UUID id) {
        Agendamento agendamento = findAgendamentoEntityById(id);

        if (agendamento.getStatus() == StatusAgendamento.REALIZADO ||
                agendamento.getStatus() == StatusAgendamento.CANCELADO) {
            throw new IllegalStateException("Status inválido para cancelamento/exclusão");
        }

        agendamento.setStatus(StatusAgendamento.CANCELADO);
        return agendamentoRepository.save(agendamento);
    }

    // --- Métodos Auxiliares Privados ---

    private Agendamento findAgendamentoEntityById(UUID id) {
        return agendamentoRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Agendamento não encontrado com ID: " + id));
    }

    private void validateDateInFuture(LocalDate data, LocalTime hora) {
        if (LocalDateTime.of(data, hora).isBefore(LocalDateTime.now())) {
            throw new BusinessException("A data e hora do agendamento devem ser futuras.");
        }
    }

    /**
     * Valida se o profissional atende naquele dia da semana e se o horário está dentro da grade.
     */
    private void existsByProfissionalAndDataAndHoraAndStatusNot(ProfissionalSaude profissional, LocalDate data, LocalTime hora) {
        DiaSemana diaSemana;
        try {
            // Tenta converter o dia da semana do Java (ex: MONDAY) para o seu Enum (ex: SEGUNDA ou MONDAY)
            diaSemana = DiaSemana.valueOf(data.getDayOfWeek().name());
        } catch (IllegalArgumentException e) {
            throw new BusinessException("Erro de configuração: Dia da semana não compatível (" + data.getDayOfWeek() + ")");
        }

        boolean atendeNesseHorario = disponibilidadeRepository.existsByProfissionalAndDiaAndHora(profissional, diaSemana, hora);

        if (!atendeNesseHorario) {
            throw new BusinessException("O profissional não realiza atendimento neste dia da semana ou horário.");
        }
    }
}