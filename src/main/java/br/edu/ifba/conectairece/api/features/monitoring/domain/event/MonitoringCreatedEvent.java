package br.edu.ifba.conectairece.api.features.monitoring.domain.event;

import br.edu.ifba.conectairece.api.features.monitoring.domain.model.Monitoring;
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
