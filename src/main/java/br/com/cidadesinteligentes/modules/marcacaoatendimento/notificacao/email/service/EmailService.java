package br.com.cidadesinteligentes.modules.marcacaoatendimento.notificacao.email.service;

import br.com.cidadesinteligentes.modules.marcacaoatendimento.agendamento.model.Agendamento;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.notificacao.email.template.ProcessadorEmailTemplate;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailService implements IEmailService {

    DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateTimeFormatter horaFormatter = DateTimeFormatter.ofPattern("HH:mm");

    private final JavaMailSender mailSender;
    private final ProcessadorEmailTemplate templateProcessor;

    @Value("${email.remetente}")
    private String remetente; // exemplo: no-reply@sistema.com


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

    private void enviar(
            String destinatario,
            String assunto,
            String template,
            Map<String, Object> variaveis
    ) {

        try {
            String html = templateProcessor.processar(template, variaveis);

            MimeMessage mensagem = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensagem, true, "UTF-8");

            helper.setTo(destinatario);
            helper.setFrom(remetente);
            helper.setSubject(assunto);
            helper.setText(html, true);

            mailSender.send(mensagem);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao enviar e-mail: " + e.getMessage(), e);
        }
    }


    // Metodos utilizados pelo modulo de marcacao de atendimento:
    @Override
    public void enviarNotificacaoConfirmaAgendamento(Agendamento agendamento) {

        enviar(
                agendamento.getPaciente().getUsuario().getEmail(),
                "Agendamento confirmado",
                "agendamento_confirmado.html",
                montarVariaveis(agendamento, true)
        );
    }

    @Override
    public void enviarNotificacaoCancelamento(Agendamento agendamento) {

        enviar(
                agendamento.getPaciente().getUsuario().getEmail(),
                "Agendamento cancelado",
                "agendamento_cancelado.html",
                montarVariaveis(agendamento, false)
        );
    }

    @Override
    public void enviarNotificacaoReagendamento(Agendamento agendamento) {

        enviar(
                agendamento.getPaciente().getUsuario().getEmail(),
                "Agendamento reagendado",
                "agendamento_reagendado.html",
                montarVariaveis(agendamento, true)
        );
    }

    @Override
    public void enviarLembreteAgendamento(Agendamento agendamento) {

        enviar(
                agendamento.getPaciente().getUsuario().getEmail(),
                "Lembrete de atendimento",
                "agendamento_lembrete.html",
                montarVariaveis(agendamento, true)
        );
    }



    // METODO UTILIZADO PARA TESTE
    @Override
    public void enviarTeste(String destinatario, String assunto, String html) {
        try {
            MimeMessage mensagem = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensagem, true, "UTF-8");

            helper.setTo(destinatario);
            helper.setFrom(remetente);
            helper.setSubject(assunto);
            helper.setText(html, true);

            mailSender.send(mensagem);

        } catch (Exception e) {
            throw new RuntimeException("Falha ao enviar e-mail de teste: " + e.getMessage(), e);
        }
    }
}
