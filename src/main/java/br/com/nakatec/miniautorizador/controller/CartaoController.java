package br.com.nakatec.miniautorizador.controller;

import br.com.nakatec.miniautorizador.dto.CartaoDto;
import br.com.nakatec.miniautorizador.service.CartaoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    @Autowired
    private CartaoService service;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<CartaoDto> criarCartao(@RequestBody CartaoDto cartaoDto) {
        try {
            return new ResponseEntity<CartaoDto>(modelMapper.map(service.criarCartao(cartaoDto), CartaoDto.class), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<CartaoDto>(cartaoDto, HttpStatus.UNPROCESSABLE_ENTITY);
        }

    }

    @GetMapping("/{numeroCartao}")
    public ResponseEntity<BigDecimal> obterSaldo(@PathVariable @NotNull String numeroCartao) {
        try {
            return ResponseEntity.ok(service.obterSaldo(numeroCartao));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
