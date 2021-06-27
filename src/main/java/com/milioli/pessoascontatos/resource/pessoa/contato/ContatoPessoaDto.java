package com.milioli.pessoascontatos.resource.pessoa.contato;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.milioli.pessoascontatos.model.entity.pessoa.Pessoa;
import com.milioli.pessoascontatos.model.entity.pessoa.contato.ContatoPessoa;
import com.milioli.pessoascontatos.resource.pessoa.PessoaDto;
import lombok.*;

import java.util.Optional;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContatoPessoaDto {

    private Long id;

    @JsonIgnoreProperties("contatos")
    private PessoaDto pessoa;

    private String nome;

    private String telefone;

    private String email;

    public static ContatoPessoa toEntity(ContatoPessoaDto dto) {
        return ContatoPessoa.builder()
                .id(dto.getId())
                .nome(dto.getNome())
                .pessoa(Optional.ofNullable(dto.getPessoa())
                        .map(PessoaDto::toEntityWithoutContatos)
                        .orElse(null))
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

    public static ContatoPessoaDto toDtoWithoutPessoa(ContatoPessoa contatoPessoa) {
        return ContatoPessoaDto.builder()
                .id(contatoPessoa.getId())
                .nome(contatoPessoa.getNome())
                .telefone(contatoPessoa.getTelefone())
                .email(contatoPessoa.getEmail())
                .build();
    }

    public static ContatoPessoa updateFromEntity(ContatoPessoa entity,
                                                 String nome,
                                                 String telefone,
                                                 String email,
                                                 Pessoa pessoa) {
        entity.setNome(nome);
        entity.setTelefone(telefone);
        entity.setEmail(email);
        entity.setPessoa(pessoa);
        return entity;
    }

}
