package med.voll.api.domain.medico;

import jakarta.persistence.QueryHint;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;


import java.time.LocalDateTime;


public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Page<Medico> findAllByAtivoTrue(Pageable paginacao);


    @Query("""
            select m from Medico m
            where
            m.ativo = true
            and
            m.especialidade = :especialidade
            and
            m.id not in(
                select c.medico.id from Consulta c
                where
                c.data = :data
                and
                c.motivoCancelamento is null
            )
            order by rand()
            limit 1
            """)
    Medico escolherMedicoAleatorioLivreNaData(Especialidade especialidade, LocalDateTime data);

    @Query("""
            select m.ativo
            from Medico m
            where
            m.id = :id
            """)
    Boolean findAtivoById(Long id);
}



//    @Query("""
//            select m.ativo
//            from Medico m
//            where
//            m.id = :id
//            """)
//    Boolean findAtivoById(Long id);
//}


//
//@Query("""
//        SELECT m FROM Medico m WHERE m.ativo = true AND m.especialidade = :especialidade AND
//        m.id NOT IN (SELECT c.medico.id FROM Consultas c WHERE c.data = :data) ORDER BY rand() LIMIT 1
//        """)
//    Medico escolherMedicoAleatorioLivreNaData(Especialidade especialidade, @NotNull @Future LocalDateTime data);


// ORDER BY rand() = ordena de forma aleatoria
//LIMIT 1 limitado ao 1º resultado
//m.id NOT IN (SELECT c.medico.id FROM Consultas c WHERE c.data = :data) ORDER BY etc
    // aqui faz uma subconsulta dentro da consulta principal, onde pesquisa dentro do resultado de especialidad
    // a data disponivel para os medicos espicialistas que passaram no 1º filtro.

    // o NOT IN diz que é pra pesquisar cujo o id nao esteja na lista da :data passada pelo  paciente. Assim traz os medico livres no dia

//select m from Medico m
//where
//m.ativo = true
//and
//m.especialidade = :especialidade
//and
//m.id not in(
//    select c.medico.id from Consulta c
//    where
//    c.data = :data
//)
//order by rand()
//limit 1