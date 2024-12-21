package med.voll.api.paciente;

import med.voll.api.endereco.DadosEndereco;

public record DadosCadastroPaciente(String nome,
                                    String email,
                                    String telefone,
                                    String cpf,
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