package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.itemavaliacaogeral.enums;

/**
 * Define os possíveis estados para um Item de Avaliação Geral.
 *
 * @author Andesson Reis
 */
public enum StatusItemAvaliacaoGeral {

    /**
     * Indica que o item foi preenchido ou finalizado corretamente.
     */
    CONCLUIDO,

    /**
     * Indica que o item possui pendências ou dados faltantes.
     */
    INCOMPLETO,

    /**
     * Indica que o item ainda não foi submetido para avaliação.
     */
    NAO_ENVIADO,

    /**
     * Indica que o item não se aplica ao contexto da solicitação atual.
     */
    NAO_APLICAVEL

}