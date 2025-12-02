package br.com.cidadesinteligentes.modules.marcacaoatendimento.agendamento.enums;

/**
 * Rastreia o ciclo de vida da consulta.
 * * @author Juan Teles Dias
 */
public enum StatusAgendamento {
    /**
     * O agendamento foi criado e está confirmado, aguardando a data da consulta.
     * (Implementa RF007 - confirmar o agendamento)
     */
    AGENDADO,

    /**
     * O paciente faltou à consulta.
     * (Implementa RF018 - registrar ausência)
     */
    AUSENTE,

    /**
     * O paciente compareceu e o atendimento foi concluído.
     */
    REALIZADO,

    /**
     * O agendamento foi cancelado pelo usuário (paciente) ou pelo profissional/administrador.
     * (Implementa RF009 - permitir cancelar)
     */
    CANCELADO,

    /**
     * O agendamento foi alterado para uma nova data/hora. O registro original
     * pode ser mantido para histórico com este status.
     * (Implementa RF009 - permitir reagendar)
     */
    REAGENDADO,

    /**
     * Status temporário opcional, indica que o agendamento foi reservado, mas
     * aguarda alguma confirmação adicional (e.g., validação de documentação).
     */
    PENDENTE
}