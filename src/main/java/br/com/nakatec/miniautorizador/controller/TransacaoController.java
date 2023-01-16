package br.com.nakatec.miniautorizador.controller;

import br.com.nakatec.miniautorizador.dto.TransacaoDto;
import br.com.nakatec.miniautorizador.service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    @Autowired
    private TransacaoService service;

    @PostMapping
    public ResponseEntity<String> realizarTransacao(@RequestBody TransacaoDto transacaoDto) throws Exception {
        try {
            return new ResponseEntity<String>(service.realizarTransacao(transacaoDto), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }

    }
}
