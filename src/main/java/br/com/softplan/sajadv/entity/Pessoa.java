package br.com.softplan.sajadv.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
public class Pessoa extends BaseEntity {

    @Column(nullable = false, length = 150)
    private String nome;

    @CPF(message = "Cpf informado deve ser um cpf válido")
    @Column(unique = true)
    private String cpf;

    @Email(message = "E-mail obrigatório")
    @Column(nullable = false, length = 400)
    private String email;

    @Column(name = "data_nascimento")
    private LocalDateTime dataNascimento;

    @Lob
    private byte[] foto;

    public Pessoa(Integer id, String nome, String cpf, String email,
                  LocalDateTime dataNascimento, byte[] foto) {
        super(id);
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.foto = foto;
    }
}
