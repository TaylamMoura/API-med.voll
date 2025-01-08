package med.voll.api.controller;


import jakarta.validation.Valid;
import med.voll.api.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


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

    @GetMapping
    public Page<DadosListagemMedico> listar(@PageableDefault(size = 10, page = 0, sort = {"nome"}) Pageable paginacao){
         return repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados){
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);
    }

    //EXCLUSÃO LÓGICA -- o marca como inativo, não o exclui de fato.
    @DeleteMapping("/{id}")
    @Transactional
    public void excluir(@PathVariable Long id){
        var medico = repository.getReferenceById(id);
        medico.excluir();
    }

//    @DeleteMapping("/{id}")
//    @Transactional
//    public  void excluir(@PathVariable Long id){
//        repository.deleteById(id);
//
//        //DESSA FORMA, EXCLUI DE FORMA DEFINITIVA.
//    }

}

//Aqui, o @Valid pede pro Spring se integrar com o bean validation
// e executar as validações no DTO.

//@PageableDefault: ordena se não houver nada na url do insomnia que ordene.
//mas o predominante é o da url
