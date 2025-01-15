package med.voll.api.domain.consultas.validacoes.agendamento;

import med.voll.api.domain.consultas.agendamento.DadosAgendamentoConsulta;

public interface ValidadorAgendamentoDeConsulta {

    void validar(DadosAgendamentoConsulta dados);
}
