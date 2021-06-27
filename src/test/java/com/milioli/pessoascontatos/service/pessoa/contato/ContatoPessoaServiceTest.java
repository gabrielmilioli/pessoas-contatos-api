package com.milioli.pessoascontatos.service.pessoa.contato;

import com.milioli.pessoascontatos.base.BaseTest;
import com.milioli.pessoascontatos.model.entity.pessoa.contato.ContatoPessoa;
import com.milioli.pessoascontatos.model.entity.pessoa.contato.ContatoPessoaTestUtils;
import com.milioli.pessoascontatos.model.repository.pessoa.contato.ContatoPessoaRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.*;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

public class ContatoPessoaServiceTest extends BaseTest {

    @SpyBean
    ContatoPessoaService service;

    @MockBean
    ContatoPessoaRepository repository;

    @SpyBean
    LocalValidatorFactoryBean validator;

    @Test
    public void deveObterContatoPorId() {
        final ContatoPessoa contatoPessoa = ContatoPessoaTestUtils.constroiContatoPessoaComId();
        Mockito.when(repository.getById(anyLong())).thenReturn(contatoPessoa);
        final ContatoPessoa byId = service.getById(contatoPessoa.getId());
        Assertions.assertThat(byId).isNotNull();
    }

    @Test
    public void deveCriarContato() {
        final ContatoPessoa contatoPessoa = ContatoPessoaTestUtils.constroiContatoPessoaSemId();

        Mockito.when(repository.save(any())).thenReturn(contatoPessoa);

        final ContatoPessoa created = service.criar(contatoPessoa);

        Assertions.assertThat(created).isNotNull();
    }

    @Test
    public void deveAtualizarContato() {
        final ContatoPessoa contatoPessoa = ContatoPessoaTestUtils.constroiContatoPessoaComId();
        final String NOME = "Nome atualizado";
        contatoPessoa.setNome(NOME);

        Mockito.when(repository.save(any())).thenReturn(contatoPessoa);

        final ContatoPessoa merged = service.atualizar(contatoPessoa);

        Assertions.assertThat(merged).isNotNull();
        Assertions.assertThat(contatoPessoa.getNome()).isEqualTo(NOME);
    }

    @Test
    public void deveDeletarContato() {
        final ContatoPessoa contatoPessoa = ContatoPessoaTestUtils.constroiContatoPessoaComId();

        service.deletar(contatoPessoa);

        assertThat(entityManager.find(ContatoPessoa.class, contatoPessoa.getId())).isNull();
        Mockito.verify(repository).delete(contatoPessoa);
    }

    @Test
    public void naoDeveDeletarContatoSemId() {
        final ContatoPessoa contatoPessoa = ContatoPessoaTestUtils.constroiContatoPessoaSemId();

        catchThrowableOfType(() ->
                service.deletar(contatoPessoa), NullPointerException.class);
    }

    @Test
    public void deveRetornarPaginacaoContatos() {
        final ContatoPessoa contatoPessoa = ContatoPessoaTestUtils.constroiContatoPessoaComId();

        final List<ContatoPessoa> contatoPessoas = Collections.singletonList(contatoPessoa);

        Mockito.when(repository.findAll(any(Example.class), any(PageRequest.class)))
                .thenReturn(new PageImpl(contatoPessoas));

        final Page<ContatoPessoa> page = service.buscar(0, 10, Sort.by("id"), new ContatoPessoa());

        Assertions.assertThat(page).isNotNull();
        Assertions.assertThat(page.getTotalElements()).isEqualTo(contatoPessoas.size());
    }

}
