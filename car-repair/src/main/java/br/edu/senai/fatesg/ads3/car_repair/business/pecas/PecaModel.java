package br.edu.senai.fatesg.ads3.car_repair.business.pecas;

import br.edu.senai.fatesg.ads3.car_repair.core.domains.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author Caio4breu
 * @author Nomscodes
 * @author GabrielNaokiUT
 */

@Data
@Entity
@Table(name = "peca")
@EqualsAndHashCode(callSuper = true)
public class PecaModel extends BaseModel {

    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;

    @Column(name = "valor_unitario", nullable = false)
    private BigDecimal valorUnitario;
}