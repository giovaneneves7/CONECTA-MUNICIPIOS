package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.acompanhamento.event;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.acompanhamento.model.Monitoring;
import lombok.Getter;

/**
 * @author Giovane Neves
 */
@Getter
public class MonitoringCreatedEvent {

    private final Monitoring monitoring;

    public MonitoringCreatedEvent(final Monitoring monitoring) {
        this.monitoring = monitoring;
    }

}
