package com.milioli.pessoascontatos.service.pessoa;

import com.milioli.pessoascontatos.model.entity.pessoa.Pessoa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

public interface PessoaServiceI {

    Pessoa getById(Long id);

    Pessoa criar(Pessoa pessoa);

    Pessoa atualizar(Pessoa pessoa);

    void deletar(Pessoa pessoa);

    Page<Pessoa> buscar(Integer offset, Integer limit, Sort sort, Pessoa filtro);

    void validarCpfExistente(Pessoa pessoa);

    void validarDataNascimento(Pessoa pessoa);

}
