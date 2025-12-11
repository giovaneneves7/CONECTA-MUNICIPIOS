package br.com.cidadesinteligentes.modules.marcacaoatendimento.notificacao.sms.service;

import br.com.cidadesinteligentes.modules.marcacaoatendimento.agendamento.model.Agendamento;

public interface ISmsService {
    void enviarNotificacaoConfirmaAgendamento(Agendamento agendamento);
    void enviarNotificacaoCancelamento(Agendamento agendamento);
    void enviarNotificacaoReagendamento(Agendamento agendamento);
    void enviarLembreteAgendamento(Agendamento agendamento);

    // METODO PARA TESTE VIA SWAGGER
    void enviarTeste(String telefoneDestino, String mensagem);
}
