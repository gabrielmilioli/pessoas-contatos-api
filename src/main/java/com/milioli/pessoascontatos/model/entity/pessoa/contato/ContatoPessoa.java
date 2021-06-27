package com.milioli.pessoascontatos.model.entity.pessoa.contato;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name = "pessoas_contatos", schema = "pessoas_owner")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContatoPessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "Informe uma pessoa")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "contatos"})
    @JoinColumn(name = "i_pessoas", referencedColumnName = "id")
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

    @Override
    public String toString() {
        return "ContatoPessoa{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", telefone='" + telefone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
