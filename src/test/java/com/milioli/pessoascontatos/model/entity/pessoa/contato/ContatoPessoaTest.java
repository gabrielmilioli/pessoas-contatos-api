package com.milioli.pessoascontatos.model.entity.pessoa.contato;

import com.milioli.pessoascontatos.base.BaseTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolationException;

public class ContatoPessoaTest extends BaseTest {

    @Test
    public void devePersistirContato() {
        final ContatoPessoa contato = new ContatoPessoa(
                null,
                ContatoPessoaTestUtils.PESSOA,
                ContatoPessoaTestUtils.NOME,
                ContatoPessoaTestUtils.TELEFONE,
                ContatoPessoaTestUtils.EMAIL
        );
        entityManager.persist(contato);
    }

    @Test
    public void deveRetornarErroAoTentarPersistirContatoSemPessoa() {
        final ContatoPessoa contato = new ContatoPessoa(
                null,
                null,
                ContatoPessoaTestUtils.NOME,
                ContatoPessoaTestUtils.TELEFONE,
                ContatoPessoaTestUtils.EMAIL
        );
        final Throwable throwable = Assertions.catchThrowable(() ->
                entityManager.persist(contato));

        Assertions.assertThat(throwable)
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("Informe uma pessoa");
    }

    @Test
    public void deveRetornarErroAoTentarPersistirContatoSemNome() {
        final ContatoPessoa contato = new ContatoPessoa(
                null,
                ContatoPessoaTestUtils.PESSOA,
                null,
                ContatoPessoaTestUtils.TELEFONE,
                ContatoPessoaTestUtils.EMAIL
        );
        final Throwable throwable = Assertions.catchThrowable(() ->
                entityManager.persist(contato));

        Assertions.assertThat(throwable)
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("Informe um nome");
    }

    @Test
    public void deveRetornarErroAoTentarPersistirContatoSemTelefone() {
        final ContatoPessoa contato = new ContatoPessoa(
                null,
                ContatoPessoaTestUtils.PESSOA,
                ContatoPessoaTestUtils.NOME,
                null,
                ContatoPessoaTestUtils.EMAIL
        );
        final Throwable throwable = Assertions.catchThrowable(() ->
                entityManager.persist(contato));

        Assertions.assertThat(throwable)
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("Informe um telefone");
    }

    @Test
    public void deveRetornarErroAoTentarPersistirContatoSemEmail() {
        final ContatoPessoa contato = new ContatoPessoa(
                null,
                ContatoPessoaTestUtils.PESSOA,
                ContatoPessoaTestUtils.NOME,
                ContatoPessoaTestUtils.TELEFONE,
                null
        );
        final Throwable throwable = Assertions.catchThrowable(() ->
                entityManager.persist(contato));

        Assertions.assertThat(throwable)
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("Informe um e-mail");
    }

    @Test
    public void deveRetornarErroAoTentarPersistirContatoComEmailInvalido() {
        final ContatoPessoa contato = new ContatoPessoa(
                null,
                ContatoPessoaTestUtils.PESSOA,
                ContatoPessoaTestUtils.NOME,
                ContatoPessoaTestUtils.TELEFONE,
                "nao eh um email"
        );
        final Throwable throwable = Assertions.catchThrowable(() ->
                entityManager.persist(contato));

        Assertions.assertThat(throwable)
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("Informe um e-mail válido");
    }

}
