package med.voll.api.domain.consultas.validacoes.agendamento;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consultas.agendamento.DadosAgendamentoConsulta;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class ValidadorHorarioFuncionamento implements ValidadorAgendamentoDeConsulta {

    public void validar(DadosAgendamentoConsulta dados){
        var dataConsulta = dados.data();

        var domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var antesDaAberturaDaClinica = dataConsulta.getHour() < 7;
        var depoisDoFechamentoDaClinica = dataConsulta.getHour() > 18;

        if (domingo || antesDaAberturaDaClinica || depoisDoFechamentoDaClinica){
            throw new ValidacaoException("Consulta fora do horário de funcionamento!");
        }
    }
}
