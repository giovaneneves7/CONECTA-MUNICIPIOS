package br.com.cidadesinteligentes.modules.marcacaoatendimento.notificacao.sms.controller;

import br.com.cidadesinteligentes.modules.marcacaoatendimento.notificacao.sms.dto.request.SmsTemplateRequestDTO;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.notificacao.sms.service.ISmsService;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.notificacao.sms.template.ProcessadorSmsTemplate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/notificacoes/sms")
@Tag(name = "smstest-controller", description = "Controller exclusivo para testes de envio de SMS via Swagger/Postman")
@RequiredArgsConstructor
public class SmsTestController {

    private final ISmsService smsService;
    private final ProcessadorSmsTemplate processadorSmsTemplate;

    @PostMapping("/enviar-template")
    @Operation(
            summary = "Envia SMS usando template",
            description = "Processa um template TXT e substitui variáveis automaticamente"
    )
    public ResponseEntity<String> enviarSmsTemplate(@RequestBody SmsTemplateRequestDTO dto) {

        Map<String, Object> variaveis = Map.of(
                "nome", dto.nome(),
                "data", dto.data(),
                "hora", dto.hora(),
                "local", dto.local()
        );

        String mensagem = processadorSmsTemplate.processar(dto.templateName(), variaveis);

        smsService.enviarTeste(dto.numero(), mensagem);

        return ResponseEntity.ok("SMS enviado para: " + dto.numero());
    }
}
