package med.voll.api.controller;


import jakarta.validation.Valid;
import med.voll.api.medico.DadosCadastroMedico;
import med.voll.api.medico.Medico;
import med.voll.api.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController     //o java sabe que essa classe é um controller
@RequestMapping("medicos")     // que esta mapeando a url /medicos

public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    @PostMapping
    @Transactional              //como vai fazer um insert no bd, precisa ter uma transação ativa com bd
    public void cadastrar(@RequestBody @Valid DadosCadastroMedico dados){ //o spring sabe que o parametro dados do metodo cadastrar, é para ser puxado do corpo da requisição.
        repository.save(new Medico(dados));

    }
}

//Aqui, o @Valid pede pro Spring se integrar com o bean validation
// e executar as validações no DTO.
