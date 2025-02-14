package com.vacinas.repositories;

import com.vacinas.model.Imunizacoes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ImunizacaoRepository extends JpaRepository<Imunizacoes, Long> {

    @Query("SELECT COUNT(v) FROM Imunizacao v WHERE v.paciente.id = :pacienteId")
    Optional<Integer> countByPacienteId(Long pacienteId);
}

