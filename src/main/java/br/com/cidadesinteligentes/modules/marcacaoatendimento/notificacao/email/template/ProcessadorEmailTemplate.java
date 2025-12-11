package br.com.cidadesinteligentes.modules.marcacaoatendimento.notificacao.email.template;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Classe responsável por carregar e processar templates HTML de e-mail.
 *
 * Os templates devem estar no caminho:
 * resources/templates/email/
 *
 * Exemplo de template:
 * <h1>Olá, {{nome}}</h1>
 *
 * As variáveis devem estar no formato:
 * {{nomeVariavel}}
 *
 * O objetivo desta classe é carregar o arquivo HTML,
 * substituir as variáveis pelo conteúdo enviado pelo service
 * e retornar o HTML final já renderizado.
 */
@Component
@RequiredArgsConstructor
public class ProcessadorEmailTemplate {

    /**
     * Processa um template HTML substituindo variáveis informadas no mapa.
     *
     * @param nomeTemplate Nome do arquivo HTML (ex: "agendamento_confirmado.html")
     * @param variaveis Mapa com as variáveis que serão inseridas no template
     * @return HTML final processado
     */
    public String processar(String nomeTemplate, Map<String, Object> variaveis) {
        try {
            // Caminho padrão dos templates dentro de resources
            String caminho = "/notificacao/marcacaoatendimento/templates/email/" + nomeTemplate;

            // Carrega o arquivo do classpath
            InputStream inputStream = getClass().getResourceAsStream(caminho);
            if (inputStream == null) {
                throw new RuntimeException("Template não encontrado no caminho: " + caminho);
            }

            // Lê o conteúdo do HTML
            String conteudo = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

            // Substitui variáveis dinamicamente
            if (variaveis != null) {
                for (Map.Entry<String, Object> entry : variaveis.entrySet()) {

                    String chave = "{{" + entry.getKey() + "}}";
                    String valor = entry.getValue() != null ? entry.getValue().toString() : "";

                    conteudo = conteudo.replace(chave, valor);
                }
            }

            return conteudo;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao processar template de e-mail: " + e.getMessage(), e);
        }
    }
}
