package br.com.nakatec.miniautorizador.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransacaoDto {
    private String numeroCartao;
    private String senhaCartao;
    private BigDecimal valor;
}
