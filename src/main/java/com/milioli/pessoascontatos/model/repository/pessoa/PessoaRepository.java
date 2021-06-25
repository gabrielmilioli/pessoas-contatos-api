package com.milioli.pessoascontatos.model.repository.pessoa;

import com.milioli.pessoascontatos.model.entity.pessoa.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    Boolean existsByCpf(String cpf);

    Pessoa getByCpf(String cpf);

}
