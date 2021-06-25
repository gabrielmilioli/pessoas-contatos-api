package com.milioli.pessoascontatos.model.repository.pessoa.contato;

import com.milioli.pessoascontatos.base.BaseTest;
import com.milioli.pessoascontatos.model.entity.pessoa.Pessoa;
import com.milioli.pessoascontatos.model.entity.pessoa.PessoaTestUtils;
import com.milioli.pessoascontatos.model.entity.pessoa.contato.ContatoPessoa;
import com.milioli.pessoascontatos.model.entity.pessoa.contato.ContatoPessoaTestUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;

public class ContatoPessoaRepositoryTest extends BaseTest {

    @Autowired
    ContatoPessoaRepository repository;

    public Pessoa persisteERetornaPessoa() {
        final Pessoa pessoa = PessoaTestUtils.constroiPessoaSemId();
        return entityManager.persist(pessoa);
    }

    public ContatoPessoa persisteERetornaContato() {
        final ContatoPessoa contatoPessoa = ContatoPessoaTestUtils.constroiContatoPessoaSemId();
        contatoPessoa.setPessoa(persisteERetornaPessoa());
        return entityManager.persist(contatoPessoa);
    }

    @Test
    public void devePersistirUmContato() {
        final ContatoPessoa contato = ContatoPessoaTestUtils.constroiContatoPessoaSemId();

        final ContatoPessoa entity = repository.save(contato);

        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getId()).isNotNull();
    }

    @Test
    public void deveAtualizarUmContato() {
        final ContatoPessoa contato = ContatoPessoaTestUtils.constroiContatoPessoaComId();
        String nome = "Contato atualizado";
        contato.setNome(nome);

        final ContatoPessoa entity = repository.save(contato);

        Assertions.assertThat(entity.getNome()).isEqualTo(nome);
    }

    @Test
    public void deveObterUmContatoPorId() {
        final ContatoPessoa contato = ContatoPessoaTestUtils.constroiContatoPessoaComId();

        final ContatoPessoa entity = repository.getById(contato.getId());

        Assertions.assertThat(entity).isNotNull();
    }

    @Test
    public void deveFiltrarContatoERetornarPaginacao() {
        final ContatoPessoa contato = persisteERetornaContato();

        final PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("id"));

        Example<ContatoPessoa> example = Example.of(contato,
                ExampleMatcher.matching()
                        .withIgnoreCase()
                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));

        final Page<ContatoPessoa> contatos = repository.findAll(example, pageRequest);

        Assertions.assertThat(contatos.getTotalElements()).isEqualTo(1);
    }

    @Test
    public void deveDeletarUmContato() {
        final ContatoPessoa contato = persisteERetornaContato();

        repository.delete(contato);

        Assertions.assertThat(entityManager.find(ContatoPessoa.class, contato.getId())).isNull();
    }

    @Test
    public void deveRetornarVerdadeiroQuandoHouverContatoComTelefoneOuEmailCadastrado() {
        final ContatoPessoa contato = persisteERetornaContato();

        final Boolean exists = repository.existsByTelefoneOrEmail(contato.getTelefone(), contato.getEmail());

        Assertions.assertThat(exists).isTrue();
    }

    @Test
    public void deveRetornarFalsoQuandoNaoHouverContatoComTelefoneOuEmailCadastrado() {
        final Boolean exists = repository.existsByTelefoneOrEmail(ContatoPessoaTestUtils.TELEFONE, ContatoPessoaTestUtils.EMAIL);

        Assertions.assertThat(exists).isFalse();
    }

}
