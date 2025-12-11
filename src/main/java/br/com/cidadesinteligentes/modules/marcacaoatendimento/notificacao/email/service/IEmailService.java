package br.com.cidadesinteligentes.modules.marcacaoatendimento.notificacao.email.service;

import br.com.cidadesinteligentes.modules.marcacaoatendimento.agendamento.model.Agendamento;

public interface IEmailService {
    void enviarNotificacaoConfirmaAgendamento(Agendamento agendamento);
    void enviarNotificacaoCancelamento(Agendamento agendamento);
    void enviarNotificacaoReagendamento(Agendamento agendamento);
    void enviarLembreteAgendamento(Agendamento agendamento);

    void enviarTeste(String destinatario, String assunto, String html);
}
