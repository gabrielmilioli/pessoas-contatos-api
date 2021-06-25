package com.milioli.pessoascontatos.service.pessoa.contato;

import com.milioli.pessoascontatos.model.entity.pessoa.contato.ContatoPessoa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

public interface ContatoPessoaServiceI {

    ContatoPessoa getById(Long id);

    ContatoPessoa salvar(ContatoPessoa contatoPessoa);

    void deletar(ContatoPessoa contatoPessoa);

    Page<ContatoPessoa> buscar(Integer offset, Integer limit, Sort sort, ContatoPessoa filtro);

    void validarEmail(String email);

}
