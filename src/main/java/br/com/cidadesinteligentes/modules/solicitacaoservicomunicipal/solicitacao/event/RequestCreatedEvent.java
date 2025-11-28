package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.event;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.model.Request;
import lombok.Getter;

/**
 * @author Giovane Neves
 */
@Getter
public class RequestCreatedEvent {

    private final Request request;

    public RequestCreatedEvent(final Request request) {
        this.request = request;
    }
}
