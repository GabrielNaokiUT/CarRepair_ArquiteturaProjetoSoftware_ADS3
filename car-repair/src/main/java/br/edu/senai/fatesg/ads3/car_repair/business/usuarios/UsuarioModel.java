package br.edu.senai.fatesg.ads3.car_repair.business.usuarios;

import br.edu.senai.fatesg.ads3.car_repair.core.domains.BaseModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "Usuario")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UsuarioModel extends BaseModel {

    @Column(name = "nome", length = 120)
    private String nome;

    @Column(name = "login", length = 60, unique = true)
    private String login;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "perfil", length = 20)
    private String perfil;

    @Column(name = "senha_hash", length = 255)
    private String senhaHash;
}
