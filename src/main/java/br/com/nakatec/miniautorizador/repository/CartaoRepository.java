package br.com.nakatec.miniautorizador.repository;

import br.com.nakatec.miniautorizador.model.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CartaoRepository extends JpaRepository<Cartao, Long> {

    @Query("from Cartao where numeroCartao = :numeroCartao")
    public Optional<Cartao> findByNumeroCartao(@Param("numeroCartao") String numeroCartao);
}
