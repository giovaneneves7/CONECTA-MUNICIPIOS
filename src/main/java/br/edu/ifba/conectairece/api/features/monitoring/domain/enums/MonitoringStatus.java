package br.edu.ifba.conectairece.api.features.monitoring.domain.enums;


import br.edu.ifba.conectairece.api.features.monitoring.domain.model.Monitoring;

/**
 *  Constant values to request's monitoring status
 * It's linked to {@link Monitoring} class, representing values that the MonitoringStatus can be.
 *
 * @author Giovane Neves
 */
public enum MonitoringStatus {
    COMPLETED,
    ANALYZING,
    PENDING,
    REJECTED,
}
