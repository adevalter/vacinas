package com.vacinas.route;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.vacinas.service.EstatisticaService;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

public class EstatisticaRoute {
    public static void processarRotas(EstatisticaService estatisticaService) {
        Spark.get("/estatisticas/imunizacoes/paciente/:id", contarVacinasPorPaciente(estatisticaService));
        Spark.get("/estatisticas/imunizacoes_atrasadas/paciente/:id", contarVacinasAtrasadas(estatisticaService));
        Spark.get("/estatisticas/imunizacoes_acima_idade/:meses", contarVacinasAcimaIdade(estatisticaService));
        Spark.get("/estatisticas/proximas_imunizacoes/paciente/:id", contarVacinasProximoMes(estatisticaService));
    }

    private static Route contarVacinasPorPaciente(EstatisticaService estatisticaService) {
        return (Request request, Response response) -> {
            response.type("application/json");

            try {
                int idPaciente = Integer.parseInt(request.params("id"));
                int quantidade = estatisticaService.contarVacinasPorPaciente(idPaciente);

                Map<String, Object> resposta = new HashMap<>();
                resposta.put("quantidade", quantidade);

                response.status(200);
                return new Gson().toJson(resposta);
            } catch (NumberFormatException e) {
                response.status(400);
                return new Gson().toJson(Map.of("erro", "ID do paciente inválido."));
            } catch (Exception e) {
                response.status(500);
                return new Gson().toJson(Map.of("erro", "Erro ao processar a solicitação."));
            }
        };
    }

    private static Route contarVacinasAtrasadas(EstatisticaService estatisticaService) {
        return (Request request, Response response) -> {
            response.type("application/json");

            try {
                int idPaciente = Integer.parseInt(request.params("id"));
                int quantidade = estatisticaService.contarVacinasAtrasadas(idPaciente);

                Map<String, Object> resposta = new HashMap<>();
                resposta.put("quantidade", quantidade);

                response.status(200);
                return new Gson().toJson(resposta);
            } catch (NumberFormatException e) {
                response.status(400);
                return new Gson().toJson(Map.of("erro", "ID do paciente inválido."));
            } catch (Exception e) {
                response.status(500);
                return new Gson().toJson(Map.of("erro", "Erro ao processar a solicitação."));
            }
        };
    }

    private static Route contarVacinasAcimaIdade(EstatisticaService estatisticaService) {
        return (Request request, Response response) -> {
            response.type("application/json");

            try {
                int idadeMeses = Integer.parseInt(request.params("meses"));
                int quantidade = estatisticaService.contarVacinasAcimaIdade(idadeMeses);

                Map<String, Object> resposta = new HashMap<>();
                resposta.put("quantidade", quantidade);

                response.status(200);
                return new Gson().toJson(resposta);
            } catch (NumberFormatException e) {
                response.status(400);
                return new Gson().toJson(Map.of("erro", "Idade inválida. Informe um número inteiro de meses."));
            } catch (Exception e) {
                response.status(500);
                return new Gson().toJson(Map.of("erro", "Erro ao processar a solicitação."));
            }
        };
    }

    private static Route contarVacinasProximoMes(EstatisticaService estatisticaService) {
        return (Request request, Response response) -> {
            response.type("application/json");

            try {
                int idPaciente = Integer.parseInt(request.params("id"));
                int quantidade = estatisticaService.contarVacinasProximoMes(idPaciente);

                Map<String, Object> resposta = new HashMap<>();
                resposta.put("quantidade", quantidade);

                response.status(200);
                return new Gson().toJson(resposta);
            } catch (NumberFormatException e) {
                response.status(400);
                return new Gson().toJson(Map.of("erro", "ID do paciente inválido."));
            } catch (Exception e) {
                response.status(500);
                return new Gson().toJson(Map.of("erro", "Erro ao processar a solicitação."));
            }
        };
    }
}
