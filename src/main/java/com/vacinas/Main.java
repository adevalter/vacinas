package com.vacinas;

import java.sql.SQLException;

import com.vacinas.route.EstatisticaRoute;
import com.vacinas.route.ImunizacoesRoute;
import com.vacinas.route.PacienteRoute;
import com.vacinas.route.VacinaRoute;
import com.vacinas.service.EstatisticaService;
import com.vacinas.service.ImunizacoesService;
import com.vacinas.service.PacienteService;
import com.vacinas.service.VacinaService;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

public class Main {

    private static final VacinaService vacinaService = new VacinaService();
    private static final PacienteService pacienteService = new PacienteService();
    private static final ImunizacoesService imunizacoesService = new ImunizacoesService();
    private static final EstatisticaService estatisticaService = new EstatisticaService();

    public static void main(String[] args) throws SQLException {

        Spark.port(8080);

        // Habilitar CORS
        // Assista https://www.youtube.com/watch?v=1V1qkh6K8Gg para entender o que é
        Spark.options("/*", new Route() {
            @Override
            public Object handle(Request requisicaoHttp, Response respostaHttp) throws Exception {

                String accessControlRequestHeaders = requisicaoHttp.headers("Access-Control-Request-Headers");

                if (accessControlRequestHeaders != null)
                    respostaHttp.header("Access-Control-Allow-Headers", accessControlRequestHeaders);

                String accessControlRequestMethod = requisicaoHttp.headers("Access-Control-Request-Method");

                if (accessControlRequestMethod != null)
                    respostaHttp.header("Access-Control-Allow-Methods", accessControlRequestMethod);

                return "OK";
            }
        });

        Spark.before(new spark.Filter() {
            @Override
            public void handle(Request requisicaoHttp, Response respostaHttp) throws Exception {
                respostaHttp.header("Access-Control-Allow-Origin", "*"); // Permite todas as origens
                respostaHttp.header("Access-Control-Allow-Methods", "OPTIONS, GET, POST, PUT, DELETE");
                respostaHttp.header("Access-Control-Allow-Headers", "Content-Type, Authorization");
            }
        });

        VacinaRoute.processarRotas(vacinaService);
        ImunizacoesRoute.processarRotas(imunizacoesService);
        PacienteRoute.processarRotas(pacienteService);
        EstatisticaRoute.processarRotas(estatisticaService);
    }
}
