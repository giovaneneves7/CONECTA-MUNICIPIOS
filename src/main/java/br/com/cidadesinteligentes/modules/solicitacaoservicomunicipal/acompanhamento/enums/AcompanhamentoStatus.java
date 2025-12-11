package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.acompanhamento.enums;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.acompanhamento.model.Acompanhamento;

/**
 * Define os valores constantes para o status de acompanhamento de uma solicitação.
 *
 * <p>Esta enumeração está vinculada à classe {@link Acompanhamento}, representando
 * os estados possíveis em que o monitoramento de uma solicitação pode se encontrar
 * durante o seu ciclo de vida.</p>
 *
 * @author Giovane Neves, Andesson Reis
 */
public enum AcompanhamentoStatus {

    /**
     * Indica que o processo de acompanhamento foi finalizado.
     */
    CONCLUIDO,

    /**
     * Indica que a solicitação está atualmente sendo analisada.
     */
    EM_ANALISE,

    /**
     * Indica que a solicitação aguarda o início do processamento ou triagem.
     */
    PENDENTE,

    /**
     * Indica que a solicitação foi indeferida ou recusada.
     */
    REJEITADO

}