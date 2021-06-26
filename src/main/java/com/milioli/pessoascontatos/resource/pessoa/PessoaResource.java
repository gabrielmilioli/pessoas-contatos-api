package com.milioli.pessoascontatos.resource.pessoa;

import com.milioli.pessoascontatos.model.entity.pessoa.Pessoa;
import com.milioli.pessoascontatos.service.pessoa.PessoaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/pessoas")
@RequiredArgsConstructor
public class PessoaResource {

    private final PessoaService service;

    @GetMapping("{id}")
    public ResponseEntity getById(@PathVariable("id") Long id) {
        final Pessoa pessoa = service.getById(id);
        return new ResponseEntity(PessoaDto.toDto(pessoa, Boolean.TRUE), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity buscar(@RequestParam(value = "offset", defaultValue = "0") Integer offset,
                                 @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                 @RequestParam(value = "sort", required = false, defaultValue = "id") String sort,
                                 @RequestParam(value = "nome", required = false) String nome,
                                 @RequestParam(value = "cpf", required = false) String cpf,
                                 @RequestParam(value = "dataNascimento", required = false) LocalDate dataNascimento) {
        final Pessoa filtro = new Pessoa(null, nome, cpf, dataNascimento, null);

        final Page<Pessoa> page = service.buscar(offset, limit, Sort.by(sort), filtro);
        return new ResponseEntity(page, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity salvar(@RequestBody PessoaDto dto) {
        try {
            final Pessoa pessoa = PessoaDto.toEntity(dto, Boolean.TRUE);
            final PessoaDto pessoaDto = PessoaDto.toDto(service.salvar(pessoa), Boolean.TRUE);

            return new ResponseEntity(pessoaDto, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id,
                                    @RequestBody PessoaDto dto) {
        try {
            final Pessoa pessoa = service.getById(id);
            final Pessoa fromRepresentation = PessoaDto.fromRepresentation(pessoa, dto);

            final PessoaDto pessoaDto = PessoaDto.toDto(service.salvar(fromRepresentation), Boolean.TRUE);

            return new ResponseEntity(pessoaDto, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity deletar(@PathVariable("id") Long id) {
        final Pessoa pessoa = service.getById(id);

        service.deletar(pessoa);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
