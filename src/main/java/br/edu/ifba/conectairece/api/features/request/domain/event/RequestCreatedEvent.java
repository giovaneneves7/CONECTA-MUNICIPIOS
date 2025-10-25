package br.edu.ifba.conectairece.api.features.request.domain.event;

import br.edu.ifba.conectairece.api.features.request.domain.model.Request;
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
