package br.edu.senai.fatesg.ads3.car_repair.business.servicos;

import br.edu.senai.fatesg.ads3.car_repair.core.domains.BaseModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name = "Servico")
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class ServicoModel extends BaseModel {

    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @Column(name = "descricao", length = 100, nullable = false)
    private String descricao;

    @Column(name = "preco", nullable = false)
    private BigDecimal preco;
}