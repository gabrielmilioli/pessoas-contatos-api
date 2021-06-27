package com.milioli.pessoascontatos.resource.pessoa.contato;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.milioli.pessoascontatos.base.BaseResourceTest;
import com.milioli.pessoascontatos.model.entity.pessoa.PessoaTestUtils;
import com.milioli.pessoascontatos.model.entity.pessoa.contato.ContatoPessoa;
import com.milioli.pessoascontatos.model.entity.pessoa.contato.ContatoPessoaTestUtils;
import com.milioli.pessoascontatos.service.pessoa.PessoaService;
import com.milioli.pessoascontatos.service.pessoa.contato.ContatoPessoaService;
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

@WebMvcTest(controllers = ContatoPessoaResource.class)
public class ContatoPessoaResourceTest extends BaseResourceTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    ContatoPessoaService service;

    @MockBean
    PessoaService pessoaService;

    @Test
    public void deveObterContatoPorId() throws Exception {
        final ContatoPessoa contato = ContatoPessoaTestUtils.constroiContatoPessoaComId();

        Mockito.when(service.getById(anyLong())).thenReturn(contato);

        final MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(API_PESSOAS_CONTATOS_PATH.concat(contato.getId().toString()))
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(contato.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("nome").value(contato.getNome()));
    }

    @Test
    public void deveRetornarPaginacaoContatoPessoa() throws Exception {
        final ContatoPessoa contato = ContatoPessoaTestUtils.constroiContatoPessoaComId();
        contato.setPessoa(PessoaTestUtils.constroiPessoaComId());

        final List<ContatoPessoa> contatos = Collections.singletonList(contato);

        Mockito.when(service.buscar(any(), any(), any(), any())).thenReturn(new PageImpl(contatos));

        final MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(API_PESSOAS_CONTATOS_PATH)
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("content").isArray());
    }

    @Test
    public void deveCriarContatoPessoa() throws Exception {
        final ContatoPessoa contatoPersistida = ContatoPessoaTestUtils.constroiContatoPessoaComId();
        final ContatoPessoaDto contatoDto = ContatoPessoaDto.toDto(ContatoPessoaTestUtils.constroiContatoPessoaSemId());

        Mockito.when(service.criar(any())).thenReturn(contatoPersistida);

        String json = new ObjectMapper().writeValueAsString(contatoDto);

        final MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API_PESSOAS_CONTATOS_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("id").isNotEmpty());
    }

}
