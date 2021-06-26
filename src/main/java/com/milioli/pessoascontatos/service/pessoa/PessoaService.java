package com.milioli.pessoascontatos.service.pessoa;

import com.milioli.pessoascontatos.exception.RegraNegocioException;
import com.milioli.pessoascontatos.model.entity.pessoa.Pessoa;
import com.milioli.pessoascontatos.model.entity.pessoa.contato.ContatoPessoa;
import com.milioli.pessoascontatos.model.repository.pessoa.PessoaRepository;
import com.milioli.pessoascontatos.service.pessoa.contato.ContatoPessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class PessoaService implements PessoaServiceI {

    @Autowired
    PessoaRepository repository;

    @Autowired
    ContatoPessoaService contatoPessoaService;

    @Autowired
    LocalValidatorFactoryBean validator;

    @Override
    public Pessoa getById(Long id) {
        return repository.getById(id);
    }

    @Override
    public Pessoa salvar(Pessoa pessoa) {
        if (Boolean.TRUE.equals(validarCpfExistente(pessoa))) {
            throw new RegraNegocioException("O CPF já está cadastrado");
        }

        if (Boolean.TRUE.equals(validarDataNascimento(pessoa))) {
            throw new RegraNegocioException("A data de nascimento é inválida");
        }

        final Pessoa save = repository.save(pessoa);

        final List<ContatoPessoa> allByPessoa = contatoPessoaService.findAllByPessoa(save);

        allByPessoa.stream()
                .filter(contatoPessoa -> pessoa.getContatos()
                        .stream()
                        .noneMatch(contato -> contato.getId().equals(contatoPessoa.getId())))
                .forEach(contatoPessoa -> contatoPessoaService.deletar(contatoPessoa));

        return save;
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

    @Override
    public Boolean validarCpfExistente(Pessoa pessoa) {
        final Pessoa byCpf = repository.getByCpf(pessoa.getCpf());
        return Objects.nonNull(byCpf) && !byCpf.getId().equals(pessoa.getId());
    }

    @Override
    public Boolean validarDataNascimento(Pessoa pessoa) {
        return pessoa.getDataNascimento().isAfter(LocalDate.now()) || pessoa.getDataNascimento().isEqual(LocalDate.now());
    }

}
