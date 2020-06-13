package br.com.portfolio.lsilva.sajadv.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.*;
import java.time.LocalDate;

import static br.com.portfolio.lsilva.sajadv.util.ValidatorMessageUtils.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pessoa extends BaseEntity {

    @NotBlank(message = "Nome - " + VALIDA_CAMPO_VALIDO)
    @Size(max = 150, message = "Nome - " + VALIDA_TAMANHO_MAXIMO)
    private String nome;

    @CPF(message = "CPF - " + VALIDA_CAMPO_VALIDO)
    @NotBlank(message = "CPF - " + VALIDA_CAMPO_OBRIGATORIO)
    @Size(min = 11, message = "CPF - " + VALIDA_TAMANHO_MINIMO)
    @Column(unique = true)
    private String cpf;

    @Email(message = "E-mail - " + VALIDA_CAMPO_VALIDO)
    @NotBlank(message = "E-mail - " + VALIDA_CAMPO_OBRIGATORIO)
    @Size(max = 400, message = "E-mail - " + VALIDA_TAMANHO_MAXIMO)
    @Column(nullable = false)
    private String email;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    private String arquivo;

    private boolean ativo = true;
}
