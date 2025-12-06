package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.acompanhamento.evento;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.acompanhamento.model.Acompanhamento;
import lombok.Getter;

/**
 * Representa o evento disparado quando um novo acompanhamento é criado no sistema.
 * * <p>Este evento encapsula o objeto {@link Acompanhamento} recém-persistido, 
 * permitindo que ouvintes (listeners) realizem ações assíncronas ou colaterais
 * baseadas nessa criação.</p>
 *
 * @author Giovane Neves, Andesson Reis
 */
@Getter
public class AcompanhamentoCriadoEvento {

    /**
     * O objeto de acompanhamento que foi criado.
     */
    private final Acompanhamento acompanhamento;

    /**
     * Constrói uma nova instância do evento de acompanhamento criado.
     *
     * @param acompanhamento O objeto {@link Acompanhamento} contendo os dados do registro criado.
     */
    public AcompanhamentoCriadoEvento(final Acompanhamento acompanhamento) {
        this.acompanhamento = acompanhamento;
    }

}