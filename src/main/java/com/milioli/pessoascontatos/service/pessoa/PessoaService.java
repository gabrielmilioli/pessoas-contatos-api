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
import java.time.LocalDateTime;
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
    public Pessoa criar(Pessoa pessoa) {
        validarCpfExistente(pessoa);
        validarDataNascimento(pessoa);

        pessoa.setDataHoraCriacao(LocalDateTime.now());

        final Pessoa save = repository.save(pessoa);

        pessoa.getContatos()
                .forEach(contatoPessoa -> {
                    contatoPessoa.setPessoa(save);
                    contatoPessoaService.criar(contatoPessoa);
                });

        return save;
    }

    @Override
    public Pessoa atualizar(Pessoa pessoa) {
        validarCpfExistente(pessoa);
        validarDataNascimento(pessoa);

        pessoa.setDataHoraAlteracao(LocalDateTime.now());

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
    public void validarCpfExistente(Pessoa pessoa) {
        final Pessoa byCpf = repository.getByCpf(pessoa.getCpf());
        if (Objects.isNull(byCpf) || byCpf.getId().equals(pessoa.getId())) {
            return;
        }
        throw new RegraNegocioException("O CPF já está cadastrado");
    }

    @Override
    public void validarDataNascimento(Pessoa pessoa) {
        if (pessoa.getDataNascimento().isBefore(LocalDate.now())) {
            return;
        }
        throw new RegraNegocioException("A data de nascimento é inválida");
    }

}
