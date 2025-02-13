package med.voll.api.domain.medico;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.endereco.Endereco;

@Table(name = "medicos")
@Entity(name = "Medico")

// NOTAÇÕES DO LOMBOK
@Getter
//@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String telefone;
    private String crm;

    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;

    @Embedded
    private Endereco endereco;

    private Boolean ativo;

    public Medico(){}

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getCrm() {
        return crm;
    }

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public Medico(DadosCadastroMedico dados) {
        this.ativo = true;
        this.nome = dados.nome();
        this.email = dados.email();
        this.telefone = dados.telefone();
        this.crm = dados.crm();
        this.especialidade = dados.especialidade();
        this.endereco = new Endereco(dados.endereco());
    }

    public void atualizarInformacoes(@Valid DadosAtualizacaoMedico dados) {
        if(dados.nome() != null){
            this.nome = dados.nome();
        }
        if(dados.telefone() != null){
            this.telefone = dados.telefone();
        }
        if (dados.endereco() != null){
            this.endereco.atualizar(dados.endereco());
        }

    }

    public void excluir() {
        this.ativo = false;

    }
}

//EMBEDDED:
//é usada para indicar que um atributo de uma entidade deve
// ser persistido como parte da mesma tabela da entidade
// principal, sem criar uma tabela separada para ele.
// na classe Endereco, precisa ter a anotação @Embeddable.

//NOMBOK
//@Getter: Essa anotação gera automaticamente os métodos getter para todos os atributos da classe.

//@NoArgsConstructor: Essa anotação gera um construtor sem
// argumentos para a classe. Um construtor é um
// método especial que é chamado quando um objeto da
//  classe é criado. Um construtor sem argumentos cria
//  um objeto com todos os atributos com seus valores
//  padrão (null para objetos, 0 para números, etc.).

//@AllArgsConstructor: Essa anotação gera um construtor que
// recebe como argumento todos os atributos da classe.
// Isso permite criar um objeto passando valores para todos
// os seus atributos diretamente no momento da criação.

//@EqualsAndHashCode(of = "id"): Essa anotação gera os
// métodos equals() e hashCode(). O método equals()
//  compara dois objetos para verificar se eles são iguais.
//  O método hashCode() retorna um código hash para o objeto,
// usado em estruturas de dados como HashMap e HashSet.
// A parte (of = "id") indica que a comparação de
// igualdade entre dois objetos Medico deve ser feita
// apenas com base no valor do atributo id.
// Dois objetos Medico são considerados iguais se e
// somente se seus ids forem iguais.