package com.vacinas.route;


import com.google.gson.*;
//import com.vacinas.core.util.StringUtil;
import com.vacinas.model.Imunizacoes;
import com.vacinas.service.ImunizacoesService;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

public class ImunizacoesRoute {

    public static void processarRotas(ImunizacoesService imunizacoesService) {
        Spark.put("/imunizacoes/:id", alterarImunizacoes(imunizacoesService));
        Spark.delete("/imunizacoes/:id",excluirImunizacoes(imunizacoesService));
        Spark.get("/imunizacoes/paciente/:id", consultarPorIdPaciente(imunizacoesService));
        Spark.get("/imunizacoes/", consultarTodasImunizacoes(imunizacoesService));
    }

    public static class LocalDateAdapter implements JsonDeserializer<LocalDate>, JsonSerializer<LocalDate> {
        private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
    
        @Override
        public JsonElement serialize(LocalDate date, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(date.format(formatter));
        }
    
        @Override
        public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return LocalDate.parse(json.getAsString(), formatter);
        }
    }

    private static Route consultarTodasImunizacoes(ImunizacoesService imunizacoesService) {
        return new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                response.type("aplication/json");

                ArrayList<Imunizacoes> listaImunizacoes = imunizacoesService.consultarTodasImunizacoes();
                if (listaImunizacoes != null) {
                    response.status(200);
                    Gson gson = new GsonBuilder()
                        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                        .create();
                    return gson.toJson(listaImunizacoes);
                } else {
                    response.status(204); // 204 No Content
                    return new Gson().toJson(new ArrayList<>());
                }

            }
        };
    }

    private static Route consultarPorIdPaciente(ImunizacoesService imunizacoesService) {
        return new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                response.type("aplication/json");

                int idPaciente = Integer.parseInt(request.params(":id"));

                Imunizacoes imunizacoes = imunizacoesService.consultarPorIdPaciente(idPaciente);
                if (imunizacoes != null) {
                    response.status(200);
                    Gson gson = new GsonBuilder()
                        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                        .create();
                    return gson.toJson(imunizacoes);

                } else {
                    response.status(404);
                    return "{\"message\": \"Não existe imunização para paciente com ID " + idPaciente + ".\"}";
                }

            }
        };
    }

    private static Route alterarImunizacoes(ImunizacoesService imunizacoesService) {
        return new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                response.type("aplication/json");
                int id = Integer.parseInt (request.params(":id"));
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                        .create();
                Imunizacoes imunizacoes = gson.fromJson(request.body(), Imunizacoes.class);

                //imunizacoes.setDataAplicacao(StringUtil.formatarDataBancoDados(imunizacoes.getDataAplicacao().toString()));
                imunizacoes.setId(id);
                int resultado = imunizacoesService.alterarImunizacoes(imunizacoes);
                if (resultado > 0) {
                    response.status(200); //200 ok
                    return "{\"message\": \"Imunização com id " + id + " foi atulizado com sucesso.\"}";
                } else {
                    response.status(209); //404 not found
                    return"{\"message\": \"A Imunização com id " + id + " não foi encontrado.\"}";
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
                    return "{\"message\": \"Imunização com id " + id + " foi excluida com sucesso.\"}";
                } else {

                    response.status(209); // 404 Not Found
                    return "{\"message\": \"A imunização com id " + id + " não foi encontrado.\"}";
                }

            }

        };
    }
}
