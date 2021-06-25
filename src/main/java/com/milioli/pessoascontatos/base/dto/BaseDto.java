package com.milioli.pessoascontatos.base.dto;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
public class BaseDto {

    private LocalDateTime dataHoraCriacao;

    private LocalDateTime dataHoraAlteracao;

}
