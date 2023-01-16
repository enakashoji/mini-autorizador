package br.com.nakatec.miniautorizador.service;

import br.com.nakatec.miniautorizador.dto.TransacaoDto;
import br.com.nakatec.miniautorizador.model.Cartao;
import br.com.nakatec.miniautorizador.model.StatusTransacao;
import br.com.nakatec.miniautorizador.model.Transacao;
import br.com.nakatec.miniautorizador.repository.TransacaoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransacaoService {

    private ModelMapper modelMapper;

    private TransacaoRepository repository;

    private CartaoService cartaoService;

    @Autowired
    public TransacaoService(ModelMapper modelMapper, TransacaoRepository repository, CartaoService cartaoService) {
        this.modelMapper = modelMapper;
        this.repository = repository;
        this.cartaoService = cartaoService;
    }

    public String realizarTransacao(TransacaoDto dto) throws RuntimeException {
        Transacao transacao = modelMapper.map(dto, Transacao.class);
        Cartao cartao = cartaoService.registrarTransacao(dto);
        transacao.setIdCartao(cartao);
        transacao = repository.save(transacao);
        return StatusTransacao.OK.name();
    }
}
