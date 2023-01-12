package br.com.nakatec.miniautorizador.service;

import br.com.nakatec.miniautorizador.dto.CartaoDto;
import br.com.nakatec.miniautorizador.model.Cartao;
import br.com.nakatec.miniautorizador.repository.CartaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CartaoServiceTest {

    @Mock
    private CartaoRepository cartaoRepository;

    private CartaoService service;

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.openMocks(this);
        Cartao cartaoMock = new Cartao(1L, "1234567890987654", "1234", new BigDecimal(500));
        service = new CartaoService(cartaoRepository);
        when(cartaoRepository.findByNumeroCartao("1234")).thenReturn(Optional.of(cartaoMock));
        when(cartaoRepository.save(cartaoMock)).thenReturn(cartaoMock);
    }

    @Test
    void deveCreditarQuinhentosAoCriarCartao(){
        CartaoDto cartaoMock = new CartaoDto();
        cartaoMock.setNumeroCartao("1234567890987654");
        cartaoMock.setSenha("1234");
        CartaoDto cartao = service.criarCartao(cartaoMock);
        assertEquals(cartao.getSaldo(), new BigDecimal(500));
    }

//    * a criação de cartões (todo cartão deverá ser criado com um saldo inicial de R$500,00)
//    * a obtenção de saldo do cartão
//    * a autorização de transações realizadas usando os cartões previamente criados como meio de pagamento
//
//    Uma transação pode ser autorizada se:
//    * o cartão existir
//    * a senha do cartão for a correta
//    * o cartão possuir saldo disponível
//    * criação de um cartão
//    * verificação do saldo do cartão recém-criado
//    * realização de diversas transações, verificando-se o saldo em seguida, até que o sistema retorne informação de saldo insuficiente
//    * realização de uma transação com senha inválida
//    * realização de uma transação com cartão inexistente
}
