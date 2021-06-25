package com.milioli.pessoascontatos.model.repository.pessoa;

import com.milioli.pessoascontatos.base.BaseTest;
import com.milioli.pessoascontatos.model.entity.pessoa.Pessoa;
import com.milioli.pessoascontatos.model.entity.pessoa.PessoaTestUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;

import javax.validation.ConstraintViolationException;

public class PessoaRepositoryTest extends BaseTest {

    @Autowired
    PessoaRepository repository;

    public Pessoa persisteERetornaPessoa() {
        final Pessoa pessoa = PessoaTestUtils.constroiPessoaSemId();
        return entityManager.persist(pessoa);
    }

    @Test
    public void deveSalvarUmaPessoa() {
        final Pessoa pessoa = PessoaTestUtils.constroiPessoaSemId();

        final Pessoa entity = repository.save(pessoa);

        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getId()).isNotNull();
    }

    @Test
    public void deveAtualizarUmaPessoa() {
        final Pessoa pessoa = PessoaTestUtils.constroiPessoaComId();
        String nome = "Gabriel Milioli";
        pessoa.setNome(nome);

        final Pessoa entity = repository.save(pessoa);

        Assertions.assertThat(entity.getNome()).isEqualTo(nome);
    }

    @Test
    public void deveObterUmaPessoaPorId() {
        final Pessoa pessoa = PessoaTestUtils.constroiPessoaComId();

        final Pessoa entity = repository.getById(pessoa.getId());

        Assertions.assertThat(entity).isNotNull();
    }

    @Test
    public void deveDeletarUmaPessoa() {
        final Pessoa pessoa = persisteERetornaPessoa();

        repository.delete(pessoa);

        Assertions.assertThat(entityManager.find(Pessoa.class, pessoa.getId())).isNull();
    }

    @Test
    public void deveFiltrarPessoaERetornarPaginacao() {
        final Pessoa pessoa = persisteERetornaPessoa();

        final PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("id"));

        Example<Pessoa> example = Example.of(pessoa,
                ExampleMatcher.matching()
                        .withIgnoreCase()
                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));

        final Page<Pessoa> pessoas = repository.findAll(example, pageRequest);

        Assertions.assertThat(pessoas.getTotalElements()).isEqualTo(1);
    }

    @Test
    public void deveRetornarVerdadeiroQuandoHouverPessoaComCpfCadastrado() {
        final Pessoa pessoa = persisteERetornaPessoa();

        final Boolean exists = repository.existsByCpf(pessoa.getCpf());

        Assertions.assertThat(exists).isTrue();
    }

    @Test
    public void deveRetornarFalsoQuandoNaoHouverPessoaComCpfCadastrado() {
        final Boolean exists = repository.existsByCpf(PessoaTestUtils.CPF);

        Assertions.assertThat(exists).isFalse();
    }

    @Test
    public void deveRetornarErroAoTentarPersistirUmaPessoaSemNome() {
        final Pessoa pessoa = PessoaTestUtils.constroiPessoaSemId();
        pessoa.setNome(null);

        final Throwable throwable = Assertions.catchThrowable(() ->
                repository.save(pessoa));

        Assertions.assertThat(throwable)
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void deveRetornarErroAoTentarPersistirUmaPessoaSemCpf() {
        final Pessoa pessoa = PessoaTestUtils.constroiPessoaSemId();
        pessoa.setCpf(null);

        final Throwable throwable = Assertions.catchThrowable(() ->
                repository.save(pessoa));

        Assertions.assertThat(throwable)
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void deveRetornarErroAoTentarPersistirUmaPessoaSemDataNascimento() {
        final Pessoa pessoa = PessoaTestUtils.constroiPessoaSemId();
        pessoa.setDataNascimento(null);

        final Throwable throwable = Assertions.catchThrowable(() ->
                repository.save(pessoa));

        Assertions.assertThat(throwable)
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void deveRetornarErroAoTentarPersistirUmaPessoaComCpfMaiorQue11() {
        final Pessoa pessoa = PessoaTestUtils.constroiPessoaSemId();
        pessoa.setCpf(pessoa.getCpf().concat("1"));

        final Throwable throwable = Assertions.catchThrowable(() ->
                repository.save(pessoa));

        Assertions.assertThat(throwable)
                .isInstanceOf(ConstraintViolationException.class);
    }

}
