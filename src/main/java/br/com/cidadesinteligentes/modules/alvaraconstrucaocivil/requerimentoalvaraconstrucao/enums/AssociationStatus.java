package br.com.cidadesinteligentes.modules.alvaraconstrucaocivil.requerimentoalvaraconstrucao.enums;

/**
 * Enumeration representing the status of an association between a construction license requirement
 * and a technical responsible.
 *
 * Possible statuses:
 * - PENDING: The association request is pending approval.
 * - APPROVED: The association has been approved.
 * - REJECTED: The association request has been rejected.
 *
 * This enum is used to track the state of the association process in the system.
 *  Author: Caio Alves
 */

public enum AssociationStatus {

    PENDING,
    APPROVED,
    REJECTED
}
