package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.evento;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.model.Solicitacao;
import lombok.Getter;

/**
 * Representa o evento disparado quando uma nova solicitação é criada no sistema.
 *
 * <p>Este evento notifica os componentes interessados de que uma nova instância de
 * {@link Solicitacao} foi persistida, permitindo o início de fluxos de trabalho
 * ou processamentos assíncronos.</p>
 *
 * @author Giovane Neves
 */
@Getter
public class SolicitacaoCriadaEvento {

    /**
     * A solicitação recém-criada.
     */
    private final Solicitacao solicitacao;

    /**
     * Constrói uma nova instância do evento de solicitação criada.
     *
     * @param solicitacao O objeto {@link Solicitacao} que gerou o evento.
     */
    public SolicitacaoCriadaEvento(final Solicitacao solicitacao) {
        this.solicitacao = solicitacao;
    }

}