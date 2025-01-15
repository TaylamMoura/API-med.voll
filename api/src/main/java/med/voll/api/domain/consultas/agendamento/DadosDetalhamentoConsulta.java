package med.voll.api.domain.consultas.agendamento;

import med.voll.api.domain.consultas.Consulta;

import java.time.LocalDateTime;

//DTO
public record DadosDetalhamentoConsulta(Long id, Long idMedico, Long dPaciente, LocalDateTime data) {

    public DadosDetalhamentoConsulta(Consulta consulta) {
        this(consulta.getId(), consulta.getMedico().getId(), consulta.getPaciente().getId(), consulta.getData());
    }
}
//construtor para nao retorna null no insomnia