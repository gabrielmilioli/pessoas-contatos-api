package com.milioli.pessoascontatos.model.entity.pessoa;

import com.milioli.pessoascontatos.model.entity.pessoa.contato.ContatoPessoa;
import com.milioli.pessoascontatos.model.entity.pessoa.contato.ContatoPessoaTestUtils;
import com.milioli.pessoascontatos.resource.pessoa.PessoaDto;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class PessoaTestUtils {

    public static Long ID = 1L;
    public static String NOME = "Gabriel";
    public static String CPF = "09763425964";
    public static LocalDate DATA_NASCIMENTO = LocalDate.of(1996, 12, 13);
    public static List<ContatoPessoa> CONTATOS = Collections.singletonList(ContatoPessoaTestUtils.constroiContatoPessoaSemId());
    public static List<ContatoPessoa> CONTATOS_SEM_ID = Collections.singletonList(ContatoPessoaTestUtils.constroiContatoPessoaSemId());

    public static Pessoa constroiPessoaSemId() {
        return buildaPessoa()
                .build();
    }

    public static Pessoa constroiPessoaComId() {
        return buildaPessoa()
                .id(ID)
                .build();
    }

    public static Pessoa.PessoaBuilder buildaPessoa() {
        return Pessoa.builder()
                .nome(NOME)
                .cpf(CPF)
                .dataNascimento(DATA_NASCIMENTO);
    }

}
