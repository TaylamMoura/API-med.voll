package med.voll.api.controller;

import med.voll.api.domain.consultas.agendamento.AgendaDeConsultas;
import med.voll.api.domain.consultas.agendamento.DadosAgendamentoConsulta;
import med.voll.api.domain.consultas.agendamento.DadosDetalhamentoConsulta;
import med.voll.api.domain.medico.Especialidade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultasControllerTest {

    @Autowired
    private MockMvc mvc;  //simula requisicoes http

    @Autowired      //simula o json de entrada
    private JacksonTester<DadosAgendamentoConsulta> dadosAgendamentoConsultaJson;  //passa ao objeto que o metodo recebe na classe que estamos testando,
                                                                                // no caso: a classe ConsultaController, recebe o objeto DadosAgendamento no metodo agendar

    @Autowired      //simula o json de saida
    private JacksonTester<DadosDetalhamentoConsulta> dadosDetalhamentoConsultaJson;

    @MockBean
    private AgendaDeConsultas agenda;

    @Test
    @DisplayName("Deveria devolver codigo http 400 qdo informaçoes estao invalidas")
    @WithMockUser //o spring passa um usuario mock, o que faz com que o security ache que estamos logado ao fazer a requisicao
    void agendar_cenario1() throws Exception {
       var response =  mvc.perform(post("/consultas"))
                .andReturn().getResponse();

       assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value()); //devolve codigo 400
    }

    @Test
    @DisplayName("Deveria devolver codigo http 200 qdo informaçoes estao validas")
    @WithMockUser
    void agendar_cenario2() throws Exception {

        var data = LocalDateTime.now().plusHours(1);
        var especialidade = Especialidade.CARDIOLOGIA;

        var dadosDetalhamento = new DadosDetalhamentoConsulta(null, 2L, 5L, data);
        when(agenda.agendar(any())).thenReturn(dadosDetalhamento);

        var response =  mvc
                .perform(
                    post("/consultas")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(dadosAgendamentoConsultaJson.write(
                                    new DadosAgendamentoConsulta(2L, 5L, data, especialidade)
                            ).getJson())
                    )
                    .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var jsonEsperado = dadosDetalhamentoConsultaJson.write(
                dadosDetalhamento).getJson();

        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }


}

//@DataJpaTest: Essa anotação é utilizada para testes que envolvem
// a camada de persistência/ testes que se concentram em repositórios JPA.
// Tsta apenas os componentes relacionados ao JPA, como repositórios e configurações de bd.
//
//@SpringBootTest: Por outro lado, essa anotação é utilizada para testes
// de integração, onde você deseja carregar o contexto completo da aplicação
// Spring. Isso inclui todos os componentes, como controladores, serviços,
// repositórios, configurações de segurança, etc. É ideal para testar a aplicação
// como um todo, garantindo que todos os componentes funcionem juntos corretamente.