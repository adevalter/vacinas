package com.vacinas.route;


import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.vacinas.model.Imunizacoes;
import com.vacinas.service.ImunizacoesService;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

public class ImunizacoesRoute {

    public static void processarRotas(ImunizacoesService imunizacoesService) {
        Spark.put("/imunizacoes/:id", alterarImunizacoes(imunizacoesService));
        Spark.delete("/imunizacoes/:id",excluirImunizacoes(imunizacoesService));
        Spark.delete("/imunizacoes/paciente/:id", excluirTodasImunizacoes(imunizacoesService));
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

    private static Route excluirTodasImunizacoes(ImunizacoesService imunizacoesService) {
        return new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                response.type("application/json");

                int pacienteId;
                try {
                    pacienteId = Integer.parseInt(request.params(":id"));
                } catch (NumberFormatException e) {
                    response.status(400); //400 bad request
                    return "{\"message\": \"ID do paciente inválido.\"}";
                }

                int resultado = imunizacoesService.excluirTodasImunizacoesPorPacienteId(pacienteId);

                if(resultado > 0) {
                    response.status(200); //200 ok
                    return "{\"message\": \"Todas as imunizações do paciente com id " + pacienteId + " foram excluídas com sucesso.\"}";
                } else {
                    response.status(400); //400 not found
                    return "{\"message\": \"Não foram encontradas imunizações para o paciente com id " + pacienteId + ".\"}";
                }

            }
                

        };
            
    }

}



