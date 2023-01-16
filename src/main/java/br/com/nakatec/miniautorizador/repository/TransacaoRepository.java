package br.com.nakatec.miniautorizador.repository;

import br.com.nakatec.miniautorizador.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
}
