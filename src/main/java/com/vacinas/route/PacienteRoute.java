package com.vacinas.route;

import java.time.LocalDate;
import java.util.List;

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
        Spark.put("/paciente/:id", atualizarPaciente(pacienteService));
        Spark.get("/paciente/:id", consultarPorId(pacienteService));
        Spark.post("/paciente", criarPaciente(pacienteService));
        Spark.delete("/paciente/:id", deletarPaciente(pacienteService));
        Spark.get("/paciente", buscarTodos(pacienteService));
    }

    private static Route atualizarPaciente(PacienteService pacienteService) {
        return new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                response.type("application/json");
                int id = Integer.parseInt(request.params(":id"));
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                        .create();
                Paciente paciente = gson.fromJson(request.body(), Paciente.class);
                paciente.setId(id);
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

    // Método para consultar paciente por ID
    private static Route consultarPorId(PacienteService pacienteService) {
        return (Request request, Response response) -> {
            int id = Integer.parseInt(request.params(":id"));

            Paciente paciente = pacienteService.consultarPorId(id);

            if (paciente != null) {
                response.status(200);
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                        .create();
                return gson.toJson(paciente);
            } else {
                response.status(404);
                return new Gson().toJson("{\"message\": \"Paciente não encontrado.\"}");
            }
        };
    }

    private static Route criarPaciente(PacienteService pacienteService) {
        return (Request request, Response response) -> {
            response.type("application/json");
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                    .create();
            Paciente paciente = gson.fromJson(request.body(), Paciente.class);
            Paciente novoPaciente = pacienteService.create(paciente);

            if (novoPaciente != null) {
                response.status(201);
                return gson.toJson(novoPaciente);
            } else {
                response.status(500);
                return new Gson().toJson("{\"message\": \"Erro ao criar paciente.\"}");
            }
        };
    }

    private static Route deletarPaciente(PacienteService pacienteService) {
        return (Request request, Response response) -> {
            response.type("application/json");
            int id = Integer.parseInt(request.params(":id"));
            boolean deletado = pacienteService.delete(id);

            if (deletado) {
                response.status(200);
                return new Gson().toJson("{\"message\": \"Paciente deletado com sucesso.\"}");
            } else {
                response.status(500);
                return new Gson().toJson("{\"message\": \"Erro ao deletar paciente.\"}");
            }
        };
    }

    private static Route buscarTodos(PacienteService pacienteService) {
        return (Request request, Response response) -> {
            response.type("application/json");
            List<Paciente> pacientes = pacienteService.buscarTodos();

            if (pacientes != null && !pacientes.isEmpty()) {
                response.status(200);
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                        .create();
                return gson.toJson(pacientes);
            } else {
                response.status(404);
                return new Gson().toJson("{\"message\": \"Nenhum paciente encontrado.\"}");
            }
        };
    }
}
