package com.vacinas.route;

import com.vacinas.repositories.ImunizacaoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/estatisticas/imunizacoes/paciente")
public class ImunitazacoesRoute {

    private final ImunizacaoRepository imunizacaoRepository;

    // Construtor correto para injeção de dependência
    public ImunitazacoesRoute(ImunizacaoRepository imunizacaoRepository) {
        this.imunizacaoRepository = imunizacaoRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getQuantidadeVacinas(@PathVariable Long id) {
        try {
            Optional<Integer> quantidade = imunizacaoRepository.countByPacienteId(id);

            if (quantidade.isPresent()) {
                return ResponseEntity.ok(quantidade.get()); // Retorna apenas o número
            } else {
                return ResponseEntity.status(404).body("Paciente não encontrado ou sem vacinas registradas.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro interno ao processar a requisição.");
        }
    }
}
