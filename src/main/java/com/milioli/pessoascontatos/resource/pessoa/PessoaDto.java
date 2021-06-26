package com.milioli.pessoascontatos.resource.pessoa;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.milioli.pessoascontatos.base.entity.dto.BaseDto;
import com.milioli.pessoascontatos.model.entity.pessoa.Pessoa;
import com.milioli.pessoascontatos.resource.pessoa.contato.ContatoPessoaDto;
import lombok.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PessoaDto extends BaseDto {

    private Long id;

    private String nome;

    private String cpf;

    private LocalDate dataNascimento;

    @JsonIgnoreProperties("pessoa")
    private List<ContatoPessoaDto> contatos;

    public static Pessoa toEntityWithoutContatos(PessoaDto pessoaDto) {
        return Pessoa.builder()
                .id(pessoaDto.getId())
                .nome(pessoaDto.getNome())
                .cpf(pessoaDto.getCpf())
                .dataNascimento(pessoaDto.getDataNascimento())
                .build();
    }

    public static Pessoa toEntity(PessoaDto pessoaDto, Boolean withContatos) {
        final Pessoa build = Pessoa.builder()
                .id(pessoaDto.getId())
                .nome(pessoaDto.getNome())
                .cpf(pessoaDto.getCpf())
                .dataNascimento(pessoaDto.getDataNascimento())
                .build();

        if (Boolean.TRUE.equals(withContatos)) {
            build.setContatos(Optional.ofNullable(pessoaDto.getContatos())
                    .map(contatoPessoaDtos -> contatoPessoaDtos
                            .stream()
                            .map(contatoPessoaDto -> ContatoPessoaDto.toEntity(contatoPessoaDto, build))
                            .collect(Collectors.toList())
                    )
                    .orElse(Collections.emptyList())
            );
        }

        return build;
    }

    public static PessoaDto toDto(Pessoa pessoa, Boolean withContatos) {
        final PessoaDto build = PessoaDto.builder()
                .id(pessoa.getId())
                .nome(pessoa.getNome())
                .cpf(pessoa.getCpf())
                .dataNascimento(pessoa.getDataNascimento())
                .build();

        if (Boolean.TRUE.equals(withContatos)) {
            build.setContatos(Optional.ofNullable(pessoa.getContatos())
                    .map(contatoPessoas -> contatoPessoas
                            .stream()
                            .map(ContatoPessoaDto::toDtoWithoutPessoa)
                            .collect(Collectors.toList())
                    )
                    .orElse(Collections.emptyList()));
        }

        return build;
    }

    public static Pessoa fromRepresentation(Pessoa entity, PessoaDto dto) {
        entity.setNome(dto.getNome());
        entity.setCpf(dto.getCpf());
        entity.setDataNascimento(dto.getDataNascimento());
        entity.setContatos(Optional.ofNullable(dto.getContatos())
                .map(contatoPessoas -> contatoPessoas
                        .stream()
                        .map(contatoPessoaDto -> ContatoPessoaDto.toEntity(contatoPessoaDto, entity))
                        .collect(Collectors.toList())
                )
                .orElse(Collections.emptyList()));
        return entity;
    }

}
