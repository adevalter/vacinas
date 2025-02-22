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
        Spark.post("/vacinas", inserirVacina(vacinaService));
        Spark.get("/vacinas", consultarTodas(vacinaService));
        Spark.get("/vacinas/faixaetaria/:faixa", consultarPorFaixaEtaria(vacinaService));
        Spark.get("/vacinas/idademaior/:meses", consultarPorIdadeMaior(vacinaService));
        Spark.get("/vacinas/naoaplicaveis/paciente/:id", consultarNaoAplicaveis(vacinaService));
    }

    private static Route inserirVacina(VacinaService vacinaService) {
        return (Request request, Response response) -> {
            response.type("application/json");
            Vacina vacina = new Gson().fromJson(request.body(), Vacina.class);
            Vacina resultado = vacinaService.inserir(vacina);
            response.status(201);
            return new Gson().toJson(resultado);
        };
    }

    private static Route consultarTodas(VacinaService vacinaService) {
        return (Request request, Response response) -> {
            response.type("application/json");
            return new Gson().toJson(vacinaService.listarTodas());
        };
    }

    private static Route consultarPorFaixaEtaria(VacinaService vacinaService) {
        return (Request request, Response response) -> {
            response.type("application/json");
            String faixa = request.params(":faixa");
            return new Gson().toJson(vacinaService.listarPorFaixaEtaria(faixa));
        };
    }

    private static Route consultarPorIdadeMaior(VacinaService vacinaService) {
        return (Request request, Response response) -> {
            response.type("application/json");
            int meses = Integer.parseInt(request.params(":meses"));
            return new Gson().toJson(vacinaService.listarPorIdadeMaior(meses));
        };
    }

    private static Route consultarNaoAplicaveis(VacinaService vacinaService) {
        return (Request request, Response response) -> {
            response.type("application/json");
            int idPaciente = Integer.parseInt(request.params(":id"));
            return new Gson().toJson(vacinaService.listarNaoAplicaveis(idPaciente));
        };
    }
}
