package com.milioli.pessoascontatos.service.pessoa;

import com.milioli.pessoascontatos.model.entity.pessoa.Pessoa;
import com.milioli.pessoascontatos.model.repository.pessoa.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Service
public class PessoaService implements PessoaServiceI {

    @Autowired
    PessoaRepository repository;

    @Autowired
    LocalValidatorFactoryBean validator;

    @Override
    public Pessoa getById(Long id) {
        return repository.getById(id);
    }

    @Override
    public Pessoa salvar(Pessoa pessoa) {
        return repository.save(pessoa);
    }

    @Override
    public void deletar(Pessoa pessoa) {
        repository.delete(pessoa);
    }

    @Override
    public Page<Pessoa> buscar(Integer offset, Integer limit, Sort sort, Pessoa filtro) {
        final PageRequest pageRequest = PageRequest.of(offset, limit, sort);

        Example<Pessoa> example = Example.of(filtro,
                ExampleMatcher.matching()
                        .withIgnoreCase()
                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));

        return repository.findAll(example, pageRequest);
    }

    @Override
    public void validarCpf(String cpf) {
        final Pessoa pessoa = new Pessoa();
        pessoa.setCpf(cpf);
        validator.validate(pessoa);
    }
}
