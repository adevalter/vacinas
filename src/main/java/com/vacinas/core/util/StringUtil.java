package com.vacinas.core.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;


public class StringUtil {
    public static LocalDate formatarDataBancoDados(String data) {
        DateTimeFormatter formatar = DateTimeFormatter.ofPattern(data);
        LocalDate dataFormatada = LocalDate.parse(data, formatar);
        return dataFormatada;
    }

    public static String retornoJsonMensagem(String mensagem) {
        Map<String, String> map = new HashMap<>();
        map.put("mensagem", mensagem);
        Gson gson = new Gson();
        return gson.toJson(map);
    }
}
