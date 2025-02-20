package com.vacinas.route;

import java.lang.reflect.Type;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.vacinas.core.util.StringUtil;
import com.vacinas.model.Imunizacoes;
import com.vacinas.service.ImunizacoesService;
import com.vacinas.model.Dose;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

public class ImunizacoesRoute {

    public static void processarRotas(ImunizacoesService imunizacoesService) {
        Spark.post("/imunizacoes", inserirImunizacoes(imunizacoesService));
        Spark.put("/imunizacoes/:id", alterarImunizacoes(imunizacoesService));
        Spark.delete("/imunizacoes/:id", excluirImunizacoes(imunizacoesService));
        Spark.get("/imunizacoes/paciente/:id", consultarPorIdPaciente(imunizacoesService));
        Spark.get("/imunizacoes", consultarTodasImunizacoes(imunizacoesService));

        Spark.get("/doses", (Request req, Response res) -> {
            res.type("application/json");
            try {
                ArrayList<Dose> doses = imunizacoesService.listarTodasDoses();
                return new Gson().toJson(doses);
            } catch (Exception e) {
                e.printStackTrace();
                res.status(500);
                return new Gson().toJson(Map.of("erro", "Erro ao buscar doses: " + e.getMessage()));
            }
        });

        Spark.get("/estatisticas/imunizacoes/paciente/:id", contarVacinasPorPaciente(imunizacoesService));
        Spark.get("/imunizacao/:id", consultarImunizacaoPorId(imunizacoesService));
        Spark.get("/estatisticas/imunizacoes_atrasadas/paciente/:id", contarVacinasAtrasadas(imunizacoesService));
        Spark.get("/estatisticas/imunizacoes_acima_idade/:meses", contarVacinasAcimaIdade(imunizacoesService));
        Spark.get("/estatisticas/proximas_imunizacoes/paciente/:id", contarVacinasProximoMes(imunizacoesService));
        Spark.get("/imunizacao/paciente/:id/aplicacao/:dt_ini/:dt_fim", consultarImunizacoesPorPacienteEIntervalo(imunizacoesService));
        Spark.delete("/imunizacoes/paciente/:id", deletarPaciente(imunizacoesService));
    }

    private static Route inserirImunizacoes(ImunizacoesService imunizacoesService) {
        return (Request request, Response response) -> {
            response.type("application/json");
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                    .create();

            Imunizacoes imunizacoes = gson.fromJson(request.body(), Imunizacoes.class);
            int resultado = imunizacoesService.inserirImunizacao(imunizacoes);

            response.status(201);
            return Map.of("mensagem", "Imunização inserida com sucesso. ID: " + resultado);
        };
    }

    public static class LocalDateAdapter implements com.google.gson.JsonDeserializer<LocalDate>, com.google.gson.JsonSerializer<LocalDate> {
        private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

        @Override
        public com.google.gson.JsonElement serialize(LocalDate date, java.lang.reflect.Type typeOfSrc, com.google.gson.JsonSerializationContext context) {
            return new com.google.gson.JsonPrimitive(date.format(formatter));
        }

        @Override
        public LocalDate deserialize(com.google.gson.JsonElement json, java.lang.reflect.Type typeOfT, com.google.gson.JsonDeserializationContext context) {
            return LocalDate.parse(json.getAsString(), formatter);
        }
    }

    private static Route consultarTodasImunizacoes(ImunizacoesService imunizacoesService) {
        return (Request request, Response response) -> {
            response.type("application/json");

            ArrayList<Imunizacoes> listaImunizacoes = imunizacoesService.consultarTodasImunizacoes();
            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();

            response.status(200);
            return gson.toJson(listaImunizacoes);
        };
    }


    private static Route consultarPorIdPaciente(ImunizacoesService imunizacoesService) {
        return new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                response.type("aplication/json");

                int idPaciente = Integer.parseInt(request.params("id"));

                Imunizacoes imunizacoes = imunizacoesService.consultarPorIdPaciente(idPaciente);
                if (imunizacoes != null) {
                    response.status(200);
                    Gson gson = new GsonBuilder()
                            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                            .create();
                    return gson.toJson(imunizacoes);

                } else {
                    response.status(404);
                    return StringUtil.retornoJsonMensagem("Não existe imunização para paciente com ID" + idPaciente);
                }

            }
        };
    }

    private static Route alterarImunizacoes(ImunizacoesService imunizacoesService) {
        return new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                response.type("aplication/json");
                try {
                    int id = Integer.parseInt(request.params("id"));
                    Gson gson = new GsonBuilder()
                            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                            .create();
                    Imunizacoes imunizacoes = gson.fromJson(request.body(), Imunizacoes.class);
                    imunizacoes.setId(id);
                    int resultado = imunizacoesService.alterarImunizacoes(imunizacoes);
                    if (resultado > 0) {
                        response.status(200); // 200 ok
                        return StringUtil.retornoJsonMensagem("Imunização com id" + id + " foi atulizado com sucesso.");
                    } else {
                        response.status(209); // 404 not found
                        return StringUtil.retornoJsonMensagem("A Imunização com id" + id + " não foi encontrada.");
                    }
                } catch (SQLIntegrityConstraintViolationException e) {
                    response.status(209); // 404 not found
                    return StringUtil.retornoJsonMensagem("Falha ao tentar alterar paciente já tomou essa dose.");

                } catch (Exception e) {
                    response.status(209); // 404 not found
                    return StringUtil.retornoJsonMensagem("Falha ao tentar alterar por favor revisar dados.");
                }

            }
        };
    }

    private static Route excluirImunizacoes(ImunizacoesService imunizacoesService) {
        return new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                response.type("aplication/json");
                int id = Integer.parseInt(request.params(":id"));

                int resultado = imunizacoesService.excluir(id);
                if (resultado > 0) {
                    response.status(200); // 200 Ok
                    return StringUtil.retornoJsonMensagem("Imunização com id" + id + " foi excluida com sucesso.");

                } else {
                    response.status(209); // 404 Not Found
                    return StringUtil.retornoJsonMensagem("A imunização com id" + id + " não foi encontrada.");
                }

            }

        };
    }

    private static Route contarVacinasPorPaciente(ImunizacoesService imunizacoesService) {
        return (Request request, Response response) -> {
            response.type("application/json");

            try {
                int idPaciente = Integer.parseInt(request.params("id"));
                int quantidade = imunizacoesService.contarVacinasPorPaciente(idPaciente);

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

    private static Route consultarImunizacaoPorId(ImunizacoesService imunizacoesService) {
        return (Request request, Response response) -> {
            response.type("application/json");

            try {
                int id = Integer.parseInt(request.params("id"));
                Imunizacoes imunizacao = imunizacoesService.consultarImunizacaoPorId(id);

                if (imunizacao != null) {
                    response.status(200);
                    Gson gson = new GsonBuilder()
                            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                            .create();
                    return gson.toJson(imunizacao);
                } else {
                    response.status(404);
                    return new Gson().toJson(Map.of("erro", "Imunização com ID " + id + " não encontrada."));
                }
            } catch (NumberFormatException e) {
                response.status(400);
                return new Gson().toJson(Map.of("erro", "ID da imunização inválido."));
            } catch (Exception e) {
                response.status(500);
                return new Gson().toJson(Map.of("erro", "Erro ao processar a solicitação."));
            }
        };
    }
    private static Route contarVacinasAtrasadas(ImunizacoesService imunizacoesService) {
        return (Request request, Response response) -> {
            response.type("application/json");

            try {
                int idPaciente = Integer.parseInt(request.params("id"));
                int quantidade = imunizacoesService.contarVacinasAtrasadas(idPaciente);

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


    private static Route contarVacinasAcimaIdade(ImunizacoesService imunizacoesService) {
        return (Request request, Response response) -> {
            response.type("application/json");

            try {
                int idadeMeses = Integer.parseInt(request.params("meses"));
                int quantidade = imunizacoesService.contarVacinasAcimaIdade(idadeMeses);

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

    private static Route contarVacinasProximoMes(ImunizacoesService imunizacoesService) {
        return (Request request, Response response) -> {
            response.type("application/json");

            try {
                int idPaciente = Integer.parseInt(request.params("id"));
                int quantidade = imunizacoesService.contarVacinasProximoMes(idPaciente);

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
    private static Route consultarImunizacoesPorPacienteEIntervalo(ImunizacoesService imunizacoesService) {
        return (Request request, Response response) -> {
            response.type("application/json");

            try {
                int idPaciente = Integer.parseInt(request.params("id"));
                LocalDate dtInicio = LocalDate.parse(request.params("dt_ini"));
                LocalDate dtFim = LocalDate.parse(request.params("dt_fim"));

                ArrayList<Imunizacoes> imunizacoes = imunizacoesService.consultarImunizacoesPorPacienteEIntervalo(idPaciente, dtInicio, dtFim);

                if (!imunizacoes.isEmpty()) {
                    response.status(200);
                    Gson gson = new GsonBuilder()
                            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                            .create();
                    return gson.toJson(imunizacoes);
                } else {
                    response.status(404);
                    return new Gson().toJson(Map.of("erro", "Nenhuma imunização encontrada para o período informado."));
                }
            } catch (NumberFormatException e) {
                response.status(400);
                return new Gson().toJson(Map.of("erro", "ID do paciente inválido."));
            } catch (Exception e) {
                response.status(500);
                return new Gson().toJson(Map.of("erro", "Erro ao processar a solicitação."));
            }
        };
    }

    private static Route deletarPaciente(ImunizacoesService imunizacoesService) {
        return (Request request, Response response) -> {
            response.type("application/json");
            int id = Integer.parseInt(request.params(":id"));
            boolean deletado = imunizacoesService.deletarPorPaciente(id);

            if (deletado) {
                response.status(200);
                return StringUtil.retornoJsonMensagem("Imunização do Paciente deletado com sucesso.");

            } else {
                response.status(500);
                return StringUtil.retornoJsonMensagem("Erro ao deletar imunização do paciente.");

            }
        };
    }
}