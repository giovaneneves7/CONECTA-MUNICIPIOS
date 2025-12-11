package br.com.cidadesinteligentes.modules.marcacaoatendimento.notificacao.sms.template;

import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
public class ProcessadorSmsTemplate {

    public String processar(String nomeTemplate, Map<String, Object> variaveis) {
        try {
            InputStream input = getClass()
                    .getResourceAsStream("/notificacao/marcacaoatendimento/templates/sms/" + nomeTemplate);

            if (input == null) {
                throw new RuntimeException("Template SMS não encontrado: " + nomeTemplate);
            }

            String conteudo = new String(input.readAllBytes(), StandardCharsets.UTF_8);

            for (var entry : variaveis.entrySet()) {
                conteudo = conteudo.replace(
                        "{{" + entry.getKey() + "}}",
                        entry.getValue().toString()
                );
            }

            return conteudo;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao processar template SMS: " + nomeTemplate, e);
        }
    }
}
