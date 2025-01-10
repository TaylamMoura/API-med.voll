package med.voll.api.domain.paciente;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.endereco.DadosEndereco;

public record DadosCadastroPaciente(

        @NotBlank
        String nome,

        @NotBlank
        @Email
        String email,

        @NotBlank
        String telefone,

        @NotBlank
        @Pattern(regexp = "\\d{3}\\.?\\d{3}\\.?\\d{3}\\-?\\d{2}")
        String cpf,

        @NotNull
        @Valid
        DadosEndereco endereco) {
}
//	"nome" : "Tay Santos",
//            "email" : "tay@paciente",
//            "telefone" : "46482108",
//            "cpf" : "12345678910",
//            "endereco" : {
//            "logradouro" : "rua gardenia",
//            "bairro" : "Jardins",
//            "cep" : "0894632",
//            "cidade" : "São Paulo",
//            "uf" : "SP",
//            "numero" : "08",
//            "complemento" : "complemento"
//            }