package med.voll.api.domain.consultas.validacoes.cancelamento;

import med.voll.api.domain.consultas.cancelamento.DadosCancelamentoConsultas;

public interface ValidadorCancelamentoDeConsulta {
    void validar(DadosCancelamentoConsultas dados);
}
