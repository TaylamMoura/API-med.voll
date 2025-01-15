package med.voll.api.domain.consultas.agendamento;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.medico.Especialidade;

import java.time.LocalDateTime;

public record DadosAgendamentoConsulta(
        Long idMedico,

        @NotNull
        Long idPaciente,

        @NotNull
        @Future  //diz que a data pe no futuro, nao pode colocar uma data que já passou
        LocalDateTime data,

        Especialidade especialidade) {
}
