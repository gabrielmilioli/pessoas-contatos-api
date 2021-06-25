package com.milioli.pessoascontatos.model.entity.pessoa.contato;

import com.milioli.pessoascontatos.model.entity.pessoa.Pessoa;
import org.mockito.Mockito;

public class ContatoPessoaTestUtils {

    public static Long ID = 1L;
    public static String NOME = "Julia";
    public static String TELEFONE = "48999999999";
    public static String EMAIL = "gabriel@email.com";
    public static Pessoa PESSOA = Mockito.mock(Pessoa.class);

    public static ContatoPessoa constroiContatoPessoaSemId() {
        return buildaContatoPessoa()
                .build();
    }

    public static ContatoPessoa constroiContatoPessoaComId() {
        return buildaContatoPessoa()
                .id(ID)
                .build();
    }

    public static ContatoPessoa.ContatoPessoaBuilder buildaContatoPessoa() {
        return ContatoPessoa.builder()
                .pessoa(PESSOA)
                .nome(NOME)
                .telefone(TELEFONE)
                .email(EMAIL);
    }

}
