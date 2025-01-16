package med.voll.api.domain.medico;

import med.voll.api.domain.consultas.Consulta;
import med.voll.api.domain.endereco.DadosEndereco;
import med.voll.api.domain.paciente.DadosCadastroPaciente;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import org.assertj.core.api.FactoryBasedNavigableListAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")  //-->lê do application-testproperties
class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private TestEntityManager em;

    //TESTE 1
    @Test
    @DisplayName("Deveria devolver null quando unico medico cadastrados nao esta disponivel na data")
    void escolherMedicoAleatorioLivreNaDataCenario1() {
        var proximaSegundaAs10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0); //cria uma data que garante que o dia sempre sera na proxima segunda.

        var medico= cadastrarMedico("Athos", "athos@voll.med", "123456", Especialidade.CARDIOLOGIA);
        var paciente= cadastrarPaciente("Artemis", "artermis@paciente.med", "12345687954");

        cadastrarConsulta(medico, paciente, proximaSegundaAs10);

        var medicoLivre = medicoRepository.escolherMedicoAleatorioLivreNaData(Especialidade.DERMATOLOGIA, proximaSegundaAs10);
        assertThat(medicoLivre).isNull(); //deve retornar null no teste
    }
    //CADA METODO DE TESTE, RODA DE MANEIRA ISOLADA, E EM CADA EXECUÇÃO SEU RESULTADO É APAGADO, PARA QUE QDO RODAR O PROX TESTE, O BD ESTÁ ZERADO

    //TESTE 2
    @Test
    @DisplayName("Deveria devolver medico quando ele estiver disponivel na data")
    void escolherMedicoAleatorioLivreNaDataCenario2() {
        //given ou arrange
        var proximaSegundaAs10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);
        var medico = cadastrarMedico("Medico", "medico@voll.med", "123456", Especialidade.CARDIOLOGIA);
        //when ou act
        var medicoLivre = medicoRepository.escolherMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA, proximaSegundaAs10);
        //then ou assert
        assertThat(medicoLivre).isEqualTo(medico);
    }

//METODOS PRIVADOS PARA O TESTE
    private DadosEndereco dadosEndereco(){
        return new DadosEndereco(
                "rua magnolia",
                "bairro",
                "08580156",
                "São Paulo",
                "SP",
                null,
                "15");
    }

    private DadosCadastroPaciente dadosPaciente(String nome, String email, String cpf){
        return new DadosCadastroPaciente(
                nome, email, "956321548", cpf, dadosEndereco()
        );
    }

    private DadosCadastroMedico dadosMedico(String nome, String email, String crm, Especialidade especialidade){
        return  new DadosCadastroMedico(
                nome, email, "135649812", crm, especialidade, dadosEndereco()
        );
    }

    private Paciente cadastrarPaciente(String nome, String email, String cpf){
        var paciente = new Paciente(dadosPaciente(nome, email, cpf));
        em.persist(paciente);
        return  paciente;
    }

    private Medico cadastrarMedico(String nome, String email, String crm, Especialidade especialidade){
        var medico = new Medico(dadosMedico(nome, email,crm, especialidade));
        em.persist(medico);
        return medico;
    }

    private void cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime data){
        em.persist(new Consulta(null, medico, paciente, data, null));
    }


}
//CLASSE DE TESTE AUTOMATIZADO

//@DataJpaTest usada para testar uma interface Repository

//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//evita com que o jpa procure um banco de dados in memoria, pois isso dará erro.

//é bom usar outro database, exclusivo para testes.
//crie um bd para isso.