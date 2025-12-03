package br.com.cidadesinteligentes.modules.marcacaoatendimento.ubs.repository;

import br.com.cidadesinteligentes.modules.marcacaoatendimento.ubs.model.UBS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * * @author Juan Teles Dias
 */
@Repository
public interface UBSRepository extends JpaRepository<UBS, UUID> {

}