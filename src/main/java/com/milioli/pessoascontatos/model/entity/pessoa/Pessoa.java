package com.milioli.pessoascontatos.model.entity.pessoa;

import com.milioli.pessoascontatos.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "pessoas", schema = BaseEntity.SCHEMA_DEFAULT)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pessoa extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotEmpty(message = "Informe um nome")
    @Length(max = 200)
    @Column(name = "nome")
    private String nome;

    @NotEmpty(message = "Informe um CPF")
    @CPF(message = "Informe um CPF válido")
    @Column(name = "cpf")
    private String cpf;

    @NotNull(message = "Informe uma data de nascimento")
    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

}
