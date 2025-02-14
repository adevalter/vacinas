package com.vacinas.service;

import com.vacinas.repositories.ImunizacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ImunitazacoesService {

    @Autowired
    private ImunizacaoRepository imunizacaoRepository;

    public Optional<Integer> getQuantidadeVacinasPorPaciente(Long pacienteId) {
        return imunizacaoRepository.countByPacienteId(pacienteId);
    }
}

