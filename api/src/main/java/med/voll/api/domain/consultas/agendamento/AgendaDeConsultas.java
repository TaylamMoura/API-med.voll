package med.voll.api.domain.consultas.agendamento;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consultas.Consulta;
import med.voll.api.domain.consultas.ConsultaRepository;
import med.voll.api.domain.consultas.cancelamento.DadosCancelamentoConsultas;
import med.voll.api.domain.consultas.validacoes.agendamento.ValidadorAgendamentoDeConsulta;
import med.voll.api.domain.consultas.validacoes.cancelamento.ValidadorCancelamentoDeConsulta;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AgendaDeConsultas {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private List<ValidadorAgendamentoDeConsulta> validadores;

    @Autowired
    private List<ValidadorCancelamentoDeConsulta> validadorCancelamento;

    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados) {

        if (!pacienteRepository.existsById(dados.idPaciente())) {
            throw new ValidacaoException("Id do paciente não existe!");
        }

        if (dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())) {
            throw new ValidacaoException("Id do médico não existe!");
        }

        validadores.forEach(v -> v.validar(dados));

        Medico medico = escolherMedico(dados);
        Paciente paciente = pacienteRepository.getReferenceById(dados.idPaciente());

        if (medico == null) {
            throw new ValidacaoException("Não existe médico disponivel para essa data.");
        }
//        if (paciente == null) {
//            throw new ValidacaoException("Erro: paciente nao encontrado");
//        }

        var consulta = new Consulta(null,
                medico,
                paciente,
                dados.data(),
                null);
        consultaRepository.save(consulta);

        return new DadosDetalhamentoConsulta(consulta);
    }


    private Medico escolherMedico(DadosAgendamentoConsulta dados) {
        if (dados.idMedico() != null) {
            return medicoRepository.getReferenceById(dados.idMedico());
        }

        if (dados.especialidade() == null) {
            throw new ValidacaoException("Especialidade é obrigatória quando médico não for escolhido!");
        }

        return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
    }


    public void cancelar(DadosCancelamentoConsultas dados) {
        if (!consultaRepository.existsById(dados.idConsulta())) {
            throw new ValidacaoException("Id da consulta informado não existe!");
        }

        validadorCancelamento.forEach(v -> v.validar(dados));

        var consulta = consultaRepository.getReferenceById(dados.idConsulta());
        consulta.cancelar(dados.motivo());
    }
}

//    private Medico escolherMedico(DadosAgendamentoConsulta dados) {
//
//        if (dados.idMedico() != null) {
//            return medicoRepository.getReferenceById(dados.idMedico());
//        }
//
//        if (dados.especialidade() == null) {
//            throw new ValidacaoException("Especialidade é obrigatória quando médico não for escolhido");
//        }
//
//        return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
//    }


//A CLASSE SERVICE EXECUTA AS REGRAS DE NEGÓCIO E VALIDAÇÕES DA APLICAÇÃO

//    private Medico escolherMedico(DadosAgendamentoConsulta dados) {
//        Medico medico = null;
//
//        if (dados.idMedico() != null) {
//            medico = medicoRepository.getReferenceById(dados.idMedico());
//            if (medico == null) {
//                throw new ValidacaoException("Médico não encontrado com o ID: " + dados.idMedico());
//            }
//            System.out.println("Médico obtido por ID: " + medico);
//        } else {
//            if (dados.especialidade() == null) {
//                throw new ValidacaoException("Especialidade é obrigatória quando médico não for escolhido");
//            }
//            medico = medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
//            if (medico == null) {
//                throw new ValidacaoException("Nenhum médico disponível encontrado para a especialidade: " + dados.especialidade());
//            }
//            System.out.println("Médico aleatório escolhido: " + medico);
//        }
//        return medico;
//    }
