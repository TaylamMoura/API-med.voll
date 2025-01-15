package med.voll.api.domain.consultas.cancelamento;

import jakarta.validation.constraints.NotNull;

public record DadosCancelamentoConsultas(
        @NotNull
        Long idConsulta,

        @NotNull
        MotivoCancelamento motivo) {
}
