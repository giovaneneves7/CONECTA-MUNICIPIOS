package br.com.cidadesinteligentes.modules.marcacaoatendimento.notificacao.email.controller;

import br.com.cidadesinteligentes.modules.marcacaoatendimento.notificacao.email.dto.request.EmailTemplateRequestDTO;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.notificacao.email.service.IEmailService;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.notificacao.email.template.ProcessadorEmailTemplate;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/notificacoes/email")
@Tag(
        name = "emailtest-controller",
        description = "Controller exclusivo para testes de envio de Email via Swagger/Postman"
)
@RequiredArgsConstructor
public class EmailTestController {

    private final IEmailService emailService;
    private final ProcessadorEmailTemplate processadorEmailTemplate;

    @PostMapping("/enviar-template")
    @Operation(
            summary = "Envia Email usando template HTML",
            description = "Processa um template HTML e substitui variáveis automaticamente"
    )
    public ResponseEntity<String> enviarEmailTemplate(@RequestBody EmailTemplateRequestDTO dto) {

        Map<String, Object> variaveis = Map.of(
                "nome", dto.nome(),
                "data", dto.data(),
                "hora", dto.hora(),
                "local", dto.local()
        );

        // Processa template HTML
        String html = processadorEmailTemplate.processar(dto.templateName(), variaveis);

        // Dispara email usando o método de teste do service
        emailService.enviarTeste(dto.to(), dto.subject(), html);

        return ResponseEntity.ok("Email enviado para: " + dto.to());
    }
}
