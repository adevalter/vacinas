package com.vacinas.route;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.vacinas.model.Vacina;
import com.vacinas.service.VacinaService;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

public class VacinaRoute {
    public static void processarRotas(VacinaService vacinaService) {
        Spark.post("/vacina", inserirVacina(vacinaService));
        Spark.get("/vacina/:id", consultarPorId(vacinaService));
        Spark.get("/vacina", consultarTodasVacinas(vacinaService));
        Spark.put("/vacina/:id", alterarVacina(vacinaService));
        Spark.delete("/vacina/:id", excluirVacina(vacinaService));
    }

    private static Route inserirVacina(VacinaService vacinaService) {
        return new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                response.type("aplication/json");
                Vacina vacina = new Gson().fromJson(request.body(), Vacina.class);
                Vacina resultado = vacinaService.inserir(vacina);
                response.status(201);
                return new Gson().toJson(resultado);
            }
        };
    }

    private static Route consultarPorId(VacinaService vacinaService) {
        return new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                response.type("aplication/json");

                int id = Integer.parseInt(request.params(":id"));

                Vacina vacina = vacinaService.consultarPorId(id);
                if (vacina != null) {
                    response.status(200);
                    return new Gson().toJson(vacina);
                } else {
                    response.status(404);
                    return "{\"message\": \"Não existe Vacina com id " + id + ".\"}";
                }

            }
        };
    }

    private static Route consultarTodasVacinas(VacinaService vacinaService) {
        return new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                response.type("aplication/json");

                ArrayList<Vacina> listaVacina = vacinaService.consultarTodasVacinas();
                if (listaVacina != null) {
                    response.status(200);
                    return new Gson().toJson(listaVacina);
                } else {
                    response.status(204); // 204 No Content
                    return new Gson().toJson(new ArrayList<>());
                }

            }
        };
    }

    private static Route alterarVacina(VacinaService vacinaService) {
        return new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                response.type("aplication/json");
                int id = Integer.parseInt(request.params(":id"));
                Vacina vacina = new Gson().fromJson(request.body(), Vacina.class);
                vacina.setId(id);
                int resultado = vacinaService.alterarVacina(vacina);
                if (resultado > 0) {
                    response.status(200); // 200 Ok
                    return "{\"message\": \"Vacina com id " + id + " foi atualizado com sucesso.\"}";
                } else {

                    response.status(209); // 404 Not Found
                    return "{\"message\": \"A vacina com id " + id + " não foi encontrado.\"}";
                }

            }

        };
    }

    private static Route excluirVacina(VacinaService vacinaService) {
        return new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                response.type("aplication/json");
                int id = Integer.parseInt(request.params(":id"));

                int resultado = vacinaService.excluir(id);
                if (resultado > 0) {
                    response.status(200); // 200 Ok
                    return "{\"message\": \"Vacina com id " + id + " foi excluida com sucesso.\"}";
                } else {

                    response.status(209); // 404 Not Found
                    return "{\"message\": \"A vacina com id " + id + " não foi encontrado.\"}";
                }

            }

        };
    }
}
