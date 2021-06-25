package com.milioli.pessoascontatos.model.entity.pessoa;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PessoaTestUtils {

    public static Long ID = 1L;
    public static String NOME = "Gabriel";
    public static String CPF = "09763425964";
    public static LocalDate DATA_NASCIMENTO = LocalDate.of(1996, 12, 13);

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
