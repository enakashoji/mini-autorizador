package br.com.nakatec.miniautorizador.repository;

import br.com.nakatec.miniautorizador.model.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CartaoRepository extends JpaRepository<Cartao, Long> {

    @Query("FROM Cartao WHERE numeroCartao = :numeroCartao")
    public Optional<Cartao> findByNumeroCartao(@Param("numeroCartao") String numeroCartao);

    @Query("FROM Cartao WHERE numeroCartao = :numeroCartao AND senha = :senha")
    public Optional<Cartao> validaSenha(@Param("numeroCartao") String numeroCartao, @Param("senha") String senha);
}
