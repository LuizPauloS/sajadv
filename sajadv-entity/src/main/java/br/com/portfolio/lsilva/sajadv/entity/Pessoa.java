package br.com.portfolio.lsilva.sajadv.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDate;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pessoa extends BaseEntity {

    @Column(length = 150)
    private String nome;

    @Column(unique = true, length = 14)
    private String cpf;

    @Column(nullable = false, length = 400)
    private String email;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    private String arquivo;

    private boolean ativo = true;
}
