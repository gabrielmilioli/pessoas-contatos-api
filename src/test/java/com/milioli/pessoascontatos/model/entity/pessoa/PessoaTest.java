package com.milioli.pessoascontatos.model.entity.pessoa;

import com.milioli.pessoascontatos.base.BaseTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolationException;

public class PessoaTest extends BaseTest {

    @Test
    public void devePersistirPessoa() {
        Pessoa pessoa = new Pessoa(
                null,
                PessoaTestUtils.NOME,
                PessoaTestUtils.CPF,
                PessoaTestUtils.DATA_NASCIMENTO,
                PessoaTestUtils.CONTATOS_SEM_ID
        );

        final Pessoa persisted = entityManager.persist(pessoa);

        Assertions.assertThat(persisted).isNotNull();
    }

    @Test
    public void deveRetornarErroAoTentarPersistirPessoaSemNome() {
        final Pessoa pessoa = new Pessoa(
                null,
                null,
                PessoaTestUtils.CPF,
                PessoaTestUtils.DATA_NASCIMENTO,
                PessoaTestUtils.CONTATOS_SEM_ID
        );

        final Throwable throwable = Assertions.catchThrowable(() ->
                entityManager.persist(pessoa));

        Assertions.assertThat(throwable)
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("Informe um nome");
    }

    @Test
    public void deveRetornarErroAoTentarPersistirPessoaSemCpf() {
        final Pessoa pessoa = new Pessoa(
                null,
                PessoaTestUtils.NOME,
                null,
                PessoaTestUtils.DATA_NASCIMENTO,
                PessoaTestUtils.CONTATOS_SEM_ID
        );

        final Throwable throwable = Assertions.catchThrowable(() ->
                entityManager.persist(pessoa));

        Assertions.assertThat(throwable)
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("Informe um CPF");
    }

    @Test
    public void deveRetornarErroAoTentarPersistirPessoaSemDataNascimento() {
        final Pessoa pessoa = new Pessoa(
                null,
                PessoaTestUtils.NOME,
                PessoaTestUtils.CPF,
                null,
                PessoaTestUtils.CONTATOS_SEM_ID
        );

        final Throwable throwable = Assertions.catchThrowable(() ->
                entityManager.persist(pessoa));

        Assertions.assertThat(throwable)
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("Informe uma data de nascimento");

    }

    @Test
    public void deveRetornarErroAoTentarPersistirPessoaComCpfInvalido() {
        final Pessoa pessoa = new Pessoa(
                null,
                PessoaTestUtils.NOME,
                "12837182344",
                PessoaTestUtils.DATA_NASCIMENTO,
                PessoaTestUtils.CONTATOS_SEM_ID
        );

        final Throwable throwable = Assertions.catchThrowable(() ->
                entityManager.persist(pessoa));

        Assertions.assertThat(throwable)
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("Informe um CPF válido");
    }

}
