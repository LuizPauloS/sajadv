package br.com.softplan.sajadv.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

import static br.com.softplan.sajadv.util.ValidatorMessageUtils.*;

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

    @JsonFormat(pattern = "dd/MM/yyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Lob
    private byte[] foto;

    @Column(columnDefinition = "TINYINT(1)")
    private boolean ativo = true;
}
