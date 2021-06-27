package com.milioli.pessoascontatos.service.pessoa.contato;

import com.milioli.pessoascontatos.model.entity.pessoa.Pessoa;
import com.milioli.pessoascontatos.model.entity.pessoa.contato.ContatoPessoa;
import com.milioli.pessoascontatos.model.repository.pessoa.contato.ContatoPessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.List;

@Service
public class ContatoPessoaService implements ContatoPessoaServiceI {

    @Autowired
    ContatoPessoaRepository repository;

    @Autowired
    LocalValidatorFactoryBean validator;

    @Override
    public ContatoPessoa getById(Long id) {
        return repository.getById(id);
    }

    @Override
    public ContatoPessoa criar(ContatoPessoa contatoPessoa) {
        return repository.save(contatoPessoa);
    }

    @Override
    public ContatoPessoa atualizar(ContatoPessoa contatoPessoa) {
        return repository.save(contatoPessoa);
    }

    @Override
    public void deletar(ContatoPessoa contatoPessoa) {
        repository.delete(contatoPessoa);
    }

    @Override
    public Page<ContatoPessoa> buscar(Integer offset, Integer limit, Sort sort, ContatoPessoa filtro) {
        final PageRequest pageRequest = PageRequest.of(offset, limit, sort);

        Example<ContatoPessoa> example = Example.of(filtro,
                ExampleMatcher.matching()
                        .withIgnoreCase()
                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));

        return repository.findAll(example, pageRequest);
    }

    public List<ContatoPessoa> findAllByPessoa(Pessoa pessoa) {
        return repository.findAllByPessoa(pessoa);
    }

}
