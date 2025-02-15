package com.vacinas.route;

import java.time.LocalDate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vacinas.core.config.LocalDateAdapter;
import com.vacinas.model.Paciente;
import com.vacinas.service.PacienteService;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

public class PacienteRoute {
    public static void processarRotas(PacienteService pacienteService) {
        Spark.put("/paciente/alterar", atualizarPaciente(pacienteService));
    }

    private static Route atualizarPaciente(PacienteService pacienteService) {
        return new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                response.type("application/json");
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                        .create();
                Paciente paciente = gson.fromJson(request.body(), Paciente.class);
                boolean atualizado = pacienteService.atualizarPaciente(paciente);

                if (atualizado) {
                    response.status(200);
                    return new Gson().toJson("{\"message\": \"Paciente atualizado com sucesso.\"}");
                } else {
                    response.status(500);
                    return new Gson().toJson("{\"message\": \"Erro ao atualizar paciente.\"}");
                }
            }
        };
    }
}
