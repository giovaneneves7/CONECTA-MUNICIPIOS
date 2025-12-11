package br.com.cidadesinteligentes.modules.marcacaoatendimento.notificacao.sms.dto.request;

/**
 * DTO usado para envio de SMS usando templates (SOMENTE PARA TESTE VIA SWAGGER)
 *
 * @author Juan
 */
public record SmsTemplateRequestDTO(
        String numero,
        String templateName,
        String nome,
        String data,
        String hora,
        String local
) {}
