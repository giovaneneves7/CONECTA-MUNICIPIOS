package br.com.cidadesinteligentes.modules.marcacaoatendimento.disponibilidadeprofissional.service;

import br.com.cidadesinteligentes.modules.marcacaoatendimento.disponibilidadeprofissional.model.DisponibilidadeProfissional;

import java.util.List;
import java.util.UUID;

/**
 * Interface que define os contratos de serviço para gestão de Disponibilidade (Grade) dos Profissionais.
 *
 * @author Juan Teles Dias
 * @since 1.0
 */
public interface IDisponibilidadeProfissionalService {

    /**
     * Cadastra um novo horário de atendimento na grade do profissional.
     *
     * @param entity Entidade contendo dia, horário e referência (ID) do profissional.
     * @return Entidade persistida.
     */
    DisponibilidadeProfissional save(DisponibilidadeProfissional entity);

    /**
     * Lista todas as disponibilidades cadastradas.
     *
     * @return Lista de entidades.
     */
    List<DisponibilidadeProfissional> findAll();

    /**
     * Busca uma disponibilidade por ID.
     *
     * @param id UUID do registro.
     * @return Entidade encontrada.
     */
    DisponibilidadeProfissional findById(UUID id);

    /**
     * Remove um horário da grade.
     *
     * @param id UUID do registro a excluir.
     * @return Entidade excluída.
     */
    DisponibilidadeProfissional delete(UUID id);

    // Nota: Geralmente não se atualiza (PUT) disponibilidade, remove-se e cria outra para evitar complexidade de validação de overlap na edição.
}