package br.com.cidadesinteligentes.modules.marcacaoatendimento.notificacao.email.dto.request;

/**
 * DTO utilizado para receber os dados necessários
 * para o envio de email via API.
 *
 * Este DTO é utilizado exclusivamente no controller
 * para facilitar testes via Swagger UI e Postman.
 *
 * @author Juan Teles
 */
public record EmailTemplateRequestDTO(
        String to,
        String subject,
        String templateName,

        // variáveis do template
        String nome,
        String data,
        String hora,
        String local
) {}

