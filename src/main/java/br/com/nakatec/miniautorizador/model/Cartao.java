package br.com.nakatec.miniautorizador.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "cartoes",
        schema = "miniautorizador",
        uniqueConstraints = {@UniqueConstraint(name = "un_numero_cartao", columnNames = {"numeroCartao"})}
)
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCartao;

    @NotBlank
    @NotNull
    @Size(min = 13, max = 16)
    private String numeroCartao;

    @NotBlank
    @NotNull
    @Size(min = 4, max = 8)
    private String senha;

    @NotNull
    @PositiveOrZero
    private BigDecimal saldo;
}
