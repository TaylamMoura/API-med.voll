package med.voll.api.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.endereco.DadosEndereco;

public record DadosCadastroMedico(

        @NotBlank   //vê se o campo nao esta em branco e se não é null
        String nome,

        @NotBlank   //notBlank é apenas para campos Strings
        @Email
        String email,

        @NotBlank
        String telefone,

        @NotBlank
        @Pattern(regexp = "\\d{4,6}")
        String crm,

        @NotNull
        Especialidade especialidade,

        @NotNull
        @Valid // faz a validação de outro DTO com bean validation
        DadosEndereco endereco) {
}

//o @Pattern serve para validar se um determinado campo de texto
// corresponde a um padrão específico definido por uma expressão regular.
// --> o \\d diz que é um digito
// {4,6} diz que o digito recebe de 4 a 6 numeros