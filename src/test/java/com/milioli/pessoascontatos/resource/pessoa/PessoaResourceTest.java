package com.milioli.pessoascontatos.resource.pessoa;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.milioli.pessoascontatos.base.BaseResourceTest;
import com.milioli.pessoascontatos.model.entity.pessoa.Pessoa;
import com.milioli.pessoascontatos.model.entity.pessoa.PessoaTestUtils;
import com.milioli.pessoascontatos.service.pessoa.PessoaService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@WebMvcTest(controllers = PessoaResource.class)
public class PessoaResourceTest extends BaseResourceTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    PessoaService service;

    @Test
    public void deveObterPessoaPorId() throws Exception {
        final Pessoa pessoa = PessoaTestUtils.constroiPessoaComId();

        Mockito.when(service.getById(anyLong())).thenReturn(pessoa);

        final MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(API_PESSOAS_PATH.concat(pessoa.getId().toString()))
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(pessoa.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("nome").value(pessoa.getNome()));
    }

    @Test
    public void deveRetornarPaginacaoPessoa() throws Exception {
        final Pessoa pessoa = PessoaTestUtils.constroiPessoaComId();

        final List<Pessoa> pessoas = Collections.singletonList(pessoa);

        Mockito.when(service.buscar(any(), any(), any(), any())).thenReturn(new PageImpl(pessoas));

        final MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(API_PESSOAS_PATH)
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("content").isArray());
    }

    @Test
    public void deveCriarPessoa() throws Exception {
        final Pessoa pessoaPersistida = PessoaTestUtils.constroiPessoaComId();
        final PessoaDto pessoaDto = PessoaDto.toDto(
                PessoaTestUtils.constroiPessoaSemId(), Boolean.TRUE);

        Mockito.when(service.criar(any())).thenReturn(pessoaPersistida);

        String json = new ObjectMapper().writeValueAsString(pessoaDto);

        final MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API_PESSOAS_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("id").isNotEmpty());
    }

}
