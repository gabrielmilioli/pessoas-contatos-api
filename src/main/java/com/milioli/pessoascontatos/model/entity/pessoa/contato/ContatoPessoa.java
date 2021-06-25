package com.milioli.pessoascontatos.model.entity.pessoa.contato;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.milioli.pessoascontatos.base.BaseEntity;
import com.milioli.pessoascontatos.model.entity.pessoa.Pessoa;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "pessoas_contatos", schema = BaseEntity.SCHEMA_DEFAULT)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContatoPessoa extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "i_pessoas")
    @NotNull(message = "Informe uma pessoa")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Pessoa pessoa;

    @NotEmpty(message = "Informe um nome")
    @Column(name = "nome")
    private String nome;

    @NotEmpty(message = "Informe um telefone")
    @Column(name = "telefone")
    private String telefone;

    @NotEmpty(message = "Informe um e-mail")
    @Column(name = "email")
    private String email;

}
