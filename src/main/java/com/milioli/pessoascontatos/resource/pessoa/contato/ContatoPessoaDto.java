package com.milioli.pessoascontatos.resource.pessoa.contato;

import com.milioli.pessoascontatos.base.dto.BaseDto;
import com.milioli.pessoascontatos.model.entity.pessoa.Pessoa;
import com.milioli.pessoascontatos.model.entity.pessoa.contato.ContatoPessoa;
import com.milioli.pessoascontatos.resource.pessoa.PessoaDto;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContatoPessoaDto extends BaseDto {

    private Long id;

    private PessoaDto pessoa;

    private String nome;

    private String telefone;

    private String email;

    public static ContatoPessoa toEntity(ContatoPessoaDto dto) {
        return ContatoPessoa.builder()
                .id(dto.getId())
                .nome(dto.getNome())
                .pessoa(PessoaDto.toEntityWithoutContatos(dto.getPessoa()))
                .telefone(dto.getTelefone())
                .email(dto.getEmail())
                .build();
    }

    public static ContatoPessoa toEntity(ContatoPessoaDto dto, Pessoa pessoa) {
        return ContatoPessoa.builder()
                .id(dto.getId())
                .nome(dto.getNome())
                .pessoa(pessoa)
                .telefone(dto.getTelefone())
                .email(dto.getEmail())
                .build();
    }

    public static ContatoPessoaDto toDto(ContatoPessoa contatoPessoa) {
        return ContatoPessoaDto.builder()
                .id(contatoPessoa.getId())
                .nome(contatoPessoa.getNome())
                .pessoa(PessoaDto.toDto(contatoPessoa.getPessoa(), Boolean.FALSE))
                .telefone(contatoPessoa.getTelefone())
                .email(contatoPessoa.getEmail())
                .build();
    }
}
