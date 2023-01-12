package br.com.nakatec.miniautorizador.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CartaoDto {

    private String numeroCartao;

    private String senha;

    private BigDecimal saldo;
}
