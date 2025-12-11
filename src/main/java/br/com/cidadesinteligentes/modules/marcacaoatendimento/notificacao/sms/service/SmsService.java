package br.com.cidadesinteligentes.modules.marcacaoatendimento.notificacao.sms.service;

import br.com.cidadesinteligentes.modules.marcacaoatendimento.agendamento.model.Agendamento;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.notificacao.sms.template.ProcessadorSmsTemplate;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SmsService implements ISmsService {

    DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateTimeFormatter horaFormatter = DateTimeFormatter.ofPattern("HH:mm");

    private final ProcessadorSmsTemplate templateProcessor;

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.phone.number}")
    private String phoneNumber;



    // Metodos auxiliares:

    private Map<String, Object> montarVariaveis(Agendamento agendamento, boolean incluirLocal) {

        Map<String, Object> variaveis = new java.util.HashMap<>();

        variaveis.put("nome", agendamento.getPaciente().getUsuario().getPessoa().getNomeCompleto());
        variaveis.put("data", agendamento.getData().format(dataFormatter));
        variaveis.put("hora", agendamento.getHora().format(horaFormatter));

        if (incluirLocal) {
            variaveis.put("local", agendamento.getServico().getUnidade().getNome());
        }

        return variaveis;
    }

    private void enviar(String telefoneDestino, String mensagem) {

        Twilio.init(accountSid, authToken);

        Message.creator(
                new PhoneNumber(telefoneDestino),
                new PhoneNumber(phoneNumber),
                mensagem
        ).create();
    }


    // Metodos utilizados pelo modulo de marcacao de atendimento:
    @Override
    public void enviarNotificacaoConfirmaAgendamento(Agendamento agendamento) {

        String mensagem = templateProcessor.processar(
                "agendamento_confirmado.txt",
                montarVariaveis(agendamento, true)
        );

        enviar(agendamento.getPaciente().getUsuario().getTelefone(), mensagem);
    }

    @Override
    public void enviarNotificacaoCancelamento(Agendamento agendamento) {

        String mensagem = templateProcessor.processar(
                "agendamento_cancelado.txt",
                montarVariaveis(agendamento, false)
        );

        enviar(agendamento.getPaciente().getUsuario().getTelefone(), mensagem);
    }

    @Override
    public void enviarNotificacaoReagendamento(Agendamento agendamento) {

        String mensagem = templateProcessor.processar(
                "agendamento_reagendado.txt",
                montarVariaveis(agendamento, true)
        );

        enviar(agendamento.getPaciente().getUsuario().getTelefone(), mensagem);
    }

    @Override
    public void enviarLembreteAgendamento(Agendamento agendamento) {

        String mensagem = templateProcessor.processar(
                "agendamento_lembrete.txt",
                montarVariaveis(agendamento, true)
        );

        enviar(agendamento.getPaciente().getUsuario().getTelefone(), mensagem);
    }


    // METODO UTILIZADO PARA TESTE
    @Override
    public void enviarTeste(String telefoneDestino, String mensagem) {

        Twilio.init(accountSid, authToken);

        Message.creator(
                new PhoneNumber(telefoneDestino),
                new PhoneNumber(phoneNumber),
                mensagem
        ).create();
    }

}
