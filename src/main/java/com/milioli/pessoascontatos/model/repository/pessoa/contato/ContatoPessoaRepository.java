package com.milioli.pessoascontatos.model.repository.pessoa.contato;

import com.milioli.pessoascontatos.model.entity.pessoa.Pessoa;
import com.milioli.pessoascontatos.model.entity.pessoa.contato.ContatoPessoa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContatoPessoaRepository extends JpaRepository<ContatoPessoa, Long> {

    Boolean existsByTelefoneOrEmail(String telefone, String email);

    List<ContatoPessoa> findAllByPessoa(Pessoa pessoa);

}
