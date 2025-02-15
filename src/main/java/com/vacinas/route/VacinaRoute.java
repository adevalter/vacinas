package com.vacinas.route;

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
}
