package com.milioli.pessoascontatos.model.entity.pessoa.contato;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.milioli.pessoascontatos.base.BaseEntity;
import com.milioli.pessoascontatos.model.entity.pessoa.Pessoa;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
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
    @NotNull(message = "Informe uma pessoa")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "i_pessoas", referencedColumnName = "id", insertable = false, updatable = false)
    private Pessoa pessoa;

    @NotEmpty(message = "Informe um nome")
    @Column(name = "nome")
    private String nome;

    @NotEmpty(message = "Informe um telefone")
    @Length(max = 11, message = "Informe um telefone válido (DDD + número)")
    @Column(name = "telefone")
    private String telefone;

    @NotEmpty(message = "Informe um e-mail")
    @Email(message = "Informe um e-mail válido")
    @Column(name = "email")
    private String email;

}
