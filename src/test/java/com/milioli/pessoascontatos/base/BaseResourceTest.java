package com.milioli.pessoascontatos.base;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class BaseResourceTest {

    public static String API_PESSOAS_PATH = "/api/pessoas/";
    public static String API_PESSOAS_CONTATOS_PATH = "/api/pessoas/contatos/";

}
