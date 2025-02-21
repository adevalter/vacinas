package com.vacinas.route;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vacinas.core.config.LocalDateAdapter;
import com.vacinas.core.util.StringUtil;
import com.vacinas.model.ConsultaPaciente;
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
                    return StringUtil.retornoJsonMensagem("Paciente atualizado com sucesso.");
                } else {
                    response.status(500);
                    return StringUtil.retornoJsonMensagem("Erro ao atualizar paciente.");
                }
            }
        };
    }

    // Método para consultar paciente por ID
    private static Route consultarPorId(PacienteService pacienteService) {
        return (Request request, Response response) -> {
            int id = Integer.parseInt(request.params(":id"));

            ConsultaPaciente paciente = pacienteService.consultarPorId(id);

            if (paciente != null) {
                response.status(200);
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                        .create();
                return gson.toJson(paciente);
            } else {
                response.status(404);
                return StringUtil.retornoJsonMensagem("Paciente não encontrado.");
            }
        };
    }


    private static Route criarPaciente(PacienteService pacienteService) {
        return new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                try {
                    response.type("application/json");
                    Gson gson = new GsonBuilder()
                            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                            .create();
                    Paciente paciente = gson.fromJson(request.body(), Paciente.class);
                    Paciente novoPaciente = pacienteService.create(paciente);

                    if (novoPaciente != null && novoPaciente.getId() > 0) {
                        response.status(201);
                        return gson.toJson(novoPaciente);
                    } else {
                        response.status(500);
                        return StringUtil.retornoJsonMensagem("Erro ao criar paciente.");
                    }
                } catch (SQLIntegrityConstraintViolationException e) {
                    response.status(209); // 404 not found
                    return StringUtil.retornoJsonMensagem("Falha ao tentar cadastrar paciente. Nome já está cadastrado.");

                } catch (Exception e) {
                    response.status(209); // 404 not found
                    return StringUtil.retornoJsonMensagem("Falha ao tentar cadastrar por favor revisar dados.");
                }

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
                return StringUtil.retornoJsonMensagem("Paciente deletado com sucesso.");
            } else {
                response.status(500);
                return StringUtil.retornoJsonMensagem("Erro ao deletar paciente.");
            }
        };
    }

    private static Route buscarTodos(PacienteService pacienteService) {
        return (Request request, Response response) -> {
            response.type("application/json");
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                    .create();

            try {
                ArrayList<ConsultaPaciente> pacientes = pacienteService.buscarTodos();

                if (pacientes == null || pacientes.isEmpty()) {
                    response.status(404);
                    return StringUtil.retornoJsonMensagem("Nenhum paciente encontrado.");
                }

                response.status(200);
                return gson.toJson(pacientes);
            } catch (Exception e) {
                e.printStackTrace();
                response.status(500);
                return StringUtil.retornoJsonMensagem("Erro interno no servidor.");
            }
        };
    }

}
