package com.vacinas.route;
import com.vacinas.model.Imunizacoes;
import com.vacinas.repositories.ImunizacaoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/imunizacao")
public class ImunizacaoController {

    private final ImunizacaoRepository imunizacaoRepository;

    public ImunizacaoController(ImunizacaoRepository imunizacaoRepository) {
        this.imunizacaoRepository = imunizacaoRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> consultarImunizacaoPorId(@PathVariable Long id) {
        try {
            Optional<Imunizacoes> imunizacao = imunizacaoRepository.findById(id);

            if (imunizacao.isPresent()) {
                return ResponseEntity.ok(imunizacao.get()); // Retorna os dados da imunização
            } else {
                return ResponseEntity.status(404).body("Imunização não encontrada.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro interno ao processar a requisição.");
        }
    }
}
