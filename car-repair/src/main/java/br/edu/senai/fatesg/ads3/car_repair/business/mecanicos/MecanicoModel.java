package br.edu.senai.fatesg.ads3.car_repair.business.mecanicos;

import br.edu.senai.fatesg.ads3.car_repair.core.domains.BaseModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "Mecanico")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class MecanicoModel extends BaseModel {

    @Column(name = "nome", length = 120)
    private String nome;

    @Column(name = "cpf", length = 14)
    private String cpf;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "telefone", length = 15)
    private String telefone;

    @Column(name = "especialidade", length = 100)
    private String especialidade;

    @Column(name = "crea", length = 20)
    private String crea;
}
