package com.milioli.pessoascontatos.service.pessoa;

import com.milioli.pessoascontatos.model.entity.pessoa.Pessoa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;

public interface PessoaServiceI {

    Pessoa getById(Long id);

    Pessoa salvar(Pessoa pessoa);

    void deletar(Pessoa pessoa);

    Page<Pessoa> buscar(Integer offset, Integer limit, Sort sort, Pessoa filtro);

    void validarCpf(String cpf);

    Boolean validarCpfExistente(Pessoa pessoa);

    Boolean validarDataNascimento(Pessoa pessoa);

}
