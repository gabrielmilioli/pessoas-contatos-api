package com.milioli.pessoascontatos.model.entity.pessoa;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.milioli.pessoascontatos.base.entity.BaseEntity;
import com.milioli.pessoascontatos.model.entity.pessoa.contato.ContatoPessoa;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pessoas", schema = BaseEntity.SCHEMA_DEFAULT)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pessoa {

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
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @JsonIgnoreProperties("pessoa")
    @OneToMany(targetEntity = ContatoPessoa.class, mappedBy = "pessoa", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ContatoPessoa> contatos = new ArrayList<>();

    @Column(name = "aud_dh_criacao")
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    private LocalDateTime dataHoraCriacao;

    @Column(name = "aud_dh_alteracao")
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    private LocalDateTime dataHoraAlteracao;

    @Override
    public String toString() {
        return "Pessoa{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", dataNascimento=" + dataNascimento +
                '}';
    }
}
