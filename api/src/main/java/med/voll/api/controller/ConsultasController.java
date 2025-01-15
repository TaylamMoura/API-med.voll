package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.consultas.agendamento.AgendaDeConsultas;
import med.voll.api.domain.consultas.agendamento.DadosAgendamentoConsulta;
import med.voll.api.domain.consultas.cancelamento.DadosCancelamentoConsultas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("consultas")
public class ConsultasController {

    @Autowired
    private AgendaDeConsultas agenda;


    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DadosAgendamentoConsulta dados){
        var dto = agenda.agendar(dados);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping
    @Transactional("/cancelar")
    public ResponseEntity cancelar (@RequestBody @Valid DadosCancelamentoConsultas dados){
        agenda.cancelar(dados);
        return ResponseEntity.noContent().build();
    }
}

//As regras de negocio ficam nas classes service, no controller, apenas chamamos estas classes.