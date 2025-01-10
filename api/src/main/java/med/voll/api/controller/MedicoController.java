package med.voll.api.controller;


import jakarta.validation.Valid;
import med.voll.api.domain.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


@RestController     //o java sabe que essa classe é um controller
@RequestMapping("medicos")     // que esta mapeando a url /medicos

public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    @PostMapping
    @Transactional              //como vai fazer um insert no bd, precisa ter uma transação ativa com bd
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder uriBuilder){ //o spring sabe que o parametro dados do metodo cadastrar, é para ser puxado do corpo da requisição.
        var medico = new Medico(dados);
        repository.save(medico);
        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico)); //-->cria um objeto ResponseEntity
    }
    //UriComponentsBuilder = cria a url http://localhost:8080, ai so colocar o complemento

    @GetMapping
    public ResponseEntity<Page<DadosListagemMedico>> listar(@PageableDefault(size = 10, page = 0, sort = {"nome"}) Pageable paginacao){
          var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
          return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados){
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);
        
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

    //EXCLUSÃO LÓGICA -- o marca como inativo, não o exclui de fato.
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id){
        var medico = repository.getReferenceById(id);
        medico.excluir();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id){

        var medico = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
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
