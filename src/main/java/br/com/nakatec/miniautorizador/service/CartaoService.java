package br.com.nakatec.miniautorizador.service;

import br.com.nakatec.miniautorizador.dto.CartaoDto;
import br.com.nakatec.miniautorizador.model.Cartao;
import br.com.nakatec.miniautorizador.repository.CartaoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;

@Service
public class CartaoService {

    private CartaoRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public CartaoService(CartaoRepository repository) {
        this.repository = repository;
    }

    public CartaoDto obterCartaoPorNumero(String numeroCartao) {
        Cartao cartao = repository.findByNumeroCartao(numeroCartao).orElseThrow(() -> new EntityNotFoundException());
        return modelMapper.map(cartao, CartaoDto.class);
    }

    public CartaoDto criarCartao(CartaoDto cartaoDto){
        Cartao cartao = modelMapper.map(cartaoDto, Cartao.class);
//        Cartao cartao = repository.findByNumeroCartao(cartaoDto.getNumeroCartao()).map()
        cartao.setSaldo(new BigDecimal(500));
        cartao = repository.save(cartao);

        return modelMapper.map(cartao, CartaoDto.class);
    }
}
