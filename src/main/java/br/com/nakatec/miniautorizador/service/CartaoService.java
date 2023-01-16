package br.com.nakatec.miniautorizador.service;

import br.com.nakatec.miniautorizador.dto.CartaoDto;
import br.com.nakatec.miniautorizador.dto.TransacaoDto;
import br.com.nakatec.miniautorizador.model.Cartao;
import br.com.nakatec.miniautorizador.model.StatusTransacao;
import br.com.nakatec.miniautorizador.repository.CartaoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Service
public class CartaoService {

    private CartaoRepository repository;

    private ModelMapper modelMapper;

    @Autowired
    public CartaoService(CartaoRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    public Cartao criarCartao(CartaoDto cartaoDto) {
        Cartao cartao = modelMapper.map(cartaoDto, Cartao.class);
        cartao.setSaldo(new BigDecimal(500));
        cartao = repository.save(cartao);
        return cartao;
    }

    public BigDecimal obterSaldo(String numeroCartao) {
        Cartao cartao = repository.findByNumeroCartao(numeroCartao).orElseThrow(() -> new EntityNotFoundException("Cartão não encontrado!"));
        return cartao.getSaldo();
    }

    public Cartao registrarTransacao(TransacaoDto transacaoDto) throws RuntimeException {
        Optional<Cartao> optionalCartao = repository.findByNumeroCartao(transacaoDto.getNumeroCartao());

        validarCartao(optionalCartao, transacaoDto.getNumeroCartao());
        validarSenha(optionalCartao, transacaoDto);
        verificarSaldo(optionalCartao, transacaoDto);

        return atualizarSaldo(optionalCartao, transacaoDto);
    }

    private Cartao atualizarSaldo(Optional<Cartao> optionalCartao, TransacaoDto transacaoDto) throws RuntimeException {

        Cartao cartao = optionalCartao
                .filter(s -> transacaoDto.getValor().compareTo(BigDecimal.ZERO) != -1)
                .orElseThrow(() -> new RuntimeException("O valor não pode ser negativo."));
        cartao.setSaldo(
                cartao.getSaldo()
                        .subtract(transacaoDto.getValor()).setScale(2, RoundingMode.HALF_UP));
        return repository.save(cartao);

    }

    private void verificarSaldo(Optional<Cartao> optionalCartao, TransacaoDto transacaoDto) throws RuntimeException {
        optionalCartao.filter(c ->
                (c.getSaldo().compareTo(transacaoDto.getValor()) != -1)
        ).orElseThrow(() -> new RuntimeException(StatusTransacao.SALDO_INSUFICIENTE.name()));
    }

    private void validarSenha(Optional<Cartao> optionalCartao, TransacaoDto transacaoDto) throws RuntimeException {
        optionalCartao.filter(c -> c.getSenha().equals(transacaoDto.getSenhaCartao())).orElseThrow(() -> new RuntimeException(StatusTransacao.SENHA_INVALIDA.name()));
    }

    private void validarCartao(Optional<Cartao> optionalCartao, String numeroCartao) throws RuntimeException {
        optionalCartao.filter(c -> c.getNumeroCartao().equals(numeroCartao)).orElseThrow(() -> new RuntimeException(StatusTransacao.CARTAO_INEXISTENTE.name()));
    }

}
