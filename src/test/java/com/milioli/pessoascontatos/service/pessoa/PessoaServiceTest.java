package com.milioli.pessoascontatos.service.pessoa;

import com.milioli.pessoascontatos.base.BaseTest;
import com.milioli.pessoascontatos.exception.RegraNegocioException;
import com.milioli.pessoascontatos.model.entity.pessoa.Pessoa;
import com.milioli.pessoascontatos.model.entity.pessoa.PessoaTestUtils;
import com.milioli.pessoascontatos.model.repository.pessoa.PessoaRepository;
import com.milioli.pessoascontatos.service.pessoa.contato.ContatoPessoaService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.*;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.mockito.ArgumentMatchers.*;

public class PessoaServiceTest extends BaseTest {

    @SpyBean
    PessoaService service;

    @MockBean
    PessoaRepository repository;

    @MockBean
    ContatoPessoaService contatoPessoaService;

    @SpyBean
    LocalValidatorFactoryBean validator;

    @Test
    public void deveObterPessoaPorId() {
        final Pessoa pessoa = PessoaTestUtils.constroiPessoaComId();
        Mockito.when(repository.getById(anyLong())).thenReturn(pessoa);
        final Pessoa byId = service.getById(pessoa.getId());
        Assertions.assertThat(byId).isNotNull();
    }

    @Test
    public void deveSalvarPessoa() {
        final Pessoa pessoa = PessoaTestUtils.constroiPessoaSemId();

        Mockito.doNothing().when(service).validarCpfExistente(any());
        Mockito.doNothing().when(service).validarDataNascimento(any());
        Mockito.when(repository.save(any())).thenReturn(pessoa);
        Mockito.when(contatoPessoaService.findAllByPessoa(any())).thenReturn(Collections.emptyList());

        final Pessoa saved = service.criar(pessoa);

        Assertions.assertThat(saved).isNotNull();
    }

    @Test
    public void deveRetornarErroAoTentarSalvarPessoaComCpfJaCadastrado() {
        final Pessoa pessoa = PessoaTestUtils.constroiPessoaSemId();

        Mockito.doThrow(RegraNegocioException.class).when(service).validarCpfExistente(any());

        final Throwable throwable = Assertions.catchThrowable(() ->
                service.criar(pessoa));

        Assertions.assertThat(throwable)
                .isInstanceOf(RegraNegocioException.class);

        Mockito.verify(repository, Mockito.never()).save(pessoa);
    }

    @Test
    public void deveRetornarErroAoTentarSalvarPessoaComDataNascimentoInvalida() {
        final Pessoa pessoa = PessoaTestUtils.constroiPessoaSemId();

        Mockito.doNothing().when(service).validarCpfExistente(any());
        Mockito.doThrow(RegraNegocioException.class).when(service).validarDataNascimento(any());

        final Throwable throwable = Assertions.catchThrowable(() ->
                service.criar(pessoa));

        Assertions.assertThat(throwable)
                .isInstanceOf(RegraNegocioException.class);

        Mockito.verify(repository, Mockito.never()).save(pessoa);
    }

    @Test
    public void deveDeletarPessoa() {
        final Pessoa pessoa = PessoaTestUtils.constroiPessoaComId();

        service.deletar(pessoa);

        assertThat(entityManager.find(Pessoa.class, pessoa.getId())).isNull();
        Mockito.verify(repository).delete(pessoa);
    }

    @Test
    public void naoDeveDeletarPessoaSemId() {
        final Pessoa pessoa = PessoaTestUtils.constroiPessoaSemId();

        catchThrowableOfType(() ->
                service.deletar(pessoa), NullPointerException.class);
    }

    @Test
    public void deveRetornarErroAoTentarValidarPessoaComCpfExistente() {
        final Pessoa pessoa = PessoaTestUtils.constroiPessoaSemId();
        final Pessoa pessoaPersistida = PessoaTestUtils.constroiPessoaComId();

        Mockito.when(repository.getByCpf(anyString())).thenReturn(pessoaPersistida);

        final Throwable throwable = Assertions.catchThrowable(() ->
                service.validarCpfExistente(pessoa));

        Assertions.assertThat(throwable)
                .isInstanceOf(RegraNegocioException.class);
    }

    @Test
    public void deveRetornarErroAoTentarValidarPessoaComDataNascimentoInvalida() {
        final Pessoa pessoa = PessoaTestUtils.constroiPessoaSemId();
        pessoa.setDataNascimento(LocalDate.now());

        final Throwable throwable = Assertions.catchThrowable(() ->
                service.validarDataNascimento(pessoa));

        Assertions.assertThat(throwable)
                .isInstanceOf(RegraNegocioException.class);
    }

    @Test
    public void deveRetornarPaginacaoPessoas() {
        final Pessoa pessoa = PessoaTestUtils.constroiPessoaComId();

        final List<Pessoa> pessoas = Collections.singletonList(pessoa);

        Mockito.when(repository.findAll(any(Example.class), any(PageRequest.class)))
                .thenReturn(new PageImpl(pessoas));

        final Page<Pessoa> page = service.buscar(0, 10, Sort.by("id"), new Pessoa());

        Assertions.assertThat(page).isNotNull();
        Assertions.assertThat(page.getTotalElements()).isEqualTo(pessoas.size());
    }

}
