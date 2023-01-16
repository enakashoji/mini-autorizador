package br.com.nakatec.miniautorizador.service;

import br.com.nakatec.miniautorizador.dto.CartaoDto;
import br.com.nakatec.miniautorizador.dto.TransacaoDto;
import br.com.nakatec.miniautorizador.model.Cartao;
import br.com.nakatec.miniautorizador.repository.CartaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CartaoServiceTest {

    @Mock(answer = Answers.RETURNS_SMART_NULLS)
    private CartaoRepository repository;

    @Mock
    private ModelMapper modelMapper;

    private CartaoService service;

    private CartaoDto cartaoDtoMock;

    private Cartao cartaoMock;

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.openMocks(this);
        service = new CartaoService(repository, modelMapper);

        cartaoMock = new Cartao(1L, "1234567890987654", "1234", new BigDecimal(500));

        cartaoDtoMock = new CartaoDto();
        cartaoDtoMock.setNumeroCartao("1234567890987654");
        cartaoDtoMock.setSenha("1234");
    }

    @Test
    void deveCreditarQuinhentosAoCriarCartao() {
        when(modelMapper.map(cartaoDtoMock, Cartao.class)).thenReturn(cartaoMock);
        when(repository.save(cartaoMock)).thenReturn(cartaoMock);
        Cartao cartao = service.criarCartao(cartaoDtoMock);
        assertEquals(cartao.getSaldo(), cartaoMock.getSaldo());
    }

    @Test
    void obterSaldoCartao() {
        when(repository.findByNumeroCartao("1234567890987654")).thenReturn(Optional.ofNullable(cartaoMock));
        assertEquals(service.obterSaldo("1234567890987654"), cartaoMock.getSaldo());
    }

    @Test
    void deveVerificarSeOCartaoExiste() {
        when(repository.findByNumeroCartao("1234567890987651")).thenReturn(Optional.ofNullable(null));
        TransacaoDto transacaoDto = new TransacaoDto();

        transacaoDto.setNumeroCartao("1234567890987651");
        transacaoDto.setSenhaCartao("1234");
        transacaoDto.setValor(BigDecimal.ONE);

        try {
            Cartao cartao = service.registrarTransacao(transacaoDto);
            fail();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "CARTAO_INEXISTENTE");
        }
    }

    @Test
    void deveVerificarSeASenhaEstaCorreta() {
        when(repository.findByNumeroCartao("1234567890987654")).thenReturn(Optional.ofNullable(cartaoMock));
        TransacaoDto transacaoDto = new TransacaoDto();

        transacaoDto.setNumeroCartao("1234567890987654");
        transacaoDto.setSenhaCartao("123");
        transacaoDto.setValor(BigDecimal.ONE);

        try {
            Cartao cartao = service.registrarTransacao(transacaoDto);
            fail();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "SENHA_INVALIDA");
        }
    }

    @Test
    void deveVerificarSeOCartaoPossuiSaldo() {
        when(repository.findByNumeroCartao("1234567890987654")).thenReturn(Optional.ofNullable(cartaoMock));
        TransacaoDto transacaoDto = new TransacaoDto();

        transacaoDto.setNumeroCartao("1234567890987654");
        transacaoDto.setSenhaCartao("1234");
        transacaoDto.setValor(new BigDecimal(1000.00));

        try {
            Cartao cartao = service.registrarTransacao(transacaoDto);
            fail();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "SALDO_INSUFICIENTE");
        }
    }

    @Test
    void deveConsumirOSaldoAteOLimite() {
        when(repository.findByNumeroCartao("1234567890987654")).thenReturn(Optional.ofNullable(cartaoMock));
        TransacaoDto transacaoDto = new TransacaoDto();

        transacaoDto.setNumeroCartao("1234567890987654");
        transacaoDto.setSenhaCartao("1234");
        transacaoDto.setValor(new BigDecimal(299.00));

        TransacaoDto transacaoDto2 = new TransacaoDto();

        transacaoDto2.setNumeroCartao("1234567890987654");
        transacaoDto2.setSenhaCartao("1234");
        transacaoDto2.setValor(new BigDecimal(201.01));

        try {
            Cartao cartao = service.registrarTransacao(transacaoDto);
            cartao = service.registrarTransacao(transacaoDto2);
            fail();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "SALDO_INSUFICIENTE");
        }
    }

}
