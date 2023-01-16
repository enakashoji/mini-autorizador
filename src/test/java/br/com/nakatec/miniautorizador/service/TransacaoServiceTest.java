package br.com.nakatec.miniautorizador.service;

import br.com.nakatec.miniautorizador.dto.TransacaoDto;
import br.com.nakatec.miniautorizador.model.Cartao;
import br.com.nakatec.miniautorizador.model.Transacao;
import br.com.nakatec.miniautorizador.repository.TransacaoRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TransacaoServiceTest {

    @Mock(answer = Answers.RETURNS_SMART_NULLS)
    private TransacaoRepository repository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private CartaoService cartaoService;

    @Mock
    private TransacaoService service;

    private Cartao cartaoMock;
    private Transacao mock;
    private TransacaoDto transacaoDtoMock;

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.openMocks(this);
        cartaoMock = new Cartao(1L, "1234567890987654", "1234", new BigDecimal(500));
        mock = new Transacao(1L, cartaoMock, BigDecimal.TEN);
        transacaoDtoMock = new TransacaoDto();

        service = new TransacaoService(modelMapper, repository, cartaoService);

        transacaoDtoMock.setValor(BigDecimal.TEN);
        transacaoDtoMock.setNumeroCartao("1234567890987654");
        transacaoDtoMock.setSenhaCartao("1234");

    }

    @Test
    void deveValidarATransacao() {
        when(cartaoService.registrarTransacao(transacaoDtoMock)).thenReturn(cartaoMock);
        when(modelMapper.map(transacaoDtoMock, Transacao.class)).thenReturn(mock);
        when(repository.save(mock)).thenReturn(mock);
        String result = service.realizarTransacao(transacaoDtoMock);
        assertEquals(result, "OK");
    }
}
