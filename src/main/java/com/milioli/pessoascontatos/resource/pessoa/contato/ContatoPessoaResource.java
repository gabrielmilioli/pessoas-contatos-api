package com.milioli.pessoascontatos.resource.pessoa.contato;

import com.milioli.pessoascontatos.model.entity.pessoa.contato.ContatoPessoa;
import com.milioli.pessoascontatos.service.pessoa.PessoaService;
import com.milioli.pessoascontatos.service.pessoa.contato.ContatoPessoaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/pessoas/contatos")
@RequiredArgsConstructor
public class ContatoPessoaResource {

    private final ContatoPessoaService service;

    private final PessoaService pessoaService;

    @GetMapping("{id}")
    public ResponseEntity getById(@PathVariable("id") Long id) {
        final ContatoPessoa contatoPessoa = service.getById(id);
        return new ResponseEntity(ContatoPessoaDto.toDto(contatoPessoa), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity buscar(@RequestParam(value = "offset", defaultValue = "0") Integer offset,
                                 @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                 @RequestParam(value = "sort", required = false, defaultValue = "id") String sort,
                                 @RequestParam(value = "nome", required = false) String nome,
                                 @RequestParam(value = "pessoa", required = false) Long pessoa,
                                 @RequestParam(value = "telefone", required = false) String telefone,
                                 @RequestParam(value = "email", required = false) String email) {
        final ContatoPessoa filtro = new ContatoPessoa(null,
                Optional.ofNullable(pessoa).map(pessoaService::getById).orElse(null),
                nome,
                telefone,
                email
        );

        final Page<ContatoPessoa> page = service.buscar(offset, limit, Sort.by(sort), filtro);
        return new ResponseEntity(page, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity salvar(@RequestBody ContatoPessoaDto dto) {
        try {
            final ContatoPessoa contatoPessoa = ContatoPessoaDto.toEntity(dto);
            final ContatoPessoaDto contatoPessoaDto = ContatoPessoaDto.toDto(service.salvar(contatoPessoa));

            return new ResponseEntity(contatoPessoaDto, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id,
                                    @RequestBody ContatoPessoaDto dto) {
        try {
            final ContatoPessoa contatoPessoa = service.getById(id);

            final ContatoPessoa fromRepresentation = ContatoPessoaDto
                    .updateFromEntity(contatoPessoa, dto.getNome(), dto.getTelefone(), dto.getEmail(), Optional.ofNullable(dto.getPessoa())
                            .map(pessoaDto -> pessoaService.getById(pessoaDto.getId())).orElse(null));

            final ContatoPessoaDto contatoPessoaDto = ContatoPessoaDto.toDto(service.salvar(fromRepresentation));

            return new ResponseEntity(contatoPessoaDto, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity deletar(@PathVariable("id") Long id) {
        final ContatoPessoa contatoPessoa = service.getById(id);

        service.deletar(contatoPessoa);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
