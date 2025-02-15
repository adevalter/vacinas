package com.vacinas.core.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StringUtil {
    public static LocalDate formatarDataBancoDados(String data) {
        DateTimeFormatter formatar = DateTimeFormatter.ofPattern(data);
        LocalDate dataFormatada = LocalDate.parse(data, formatar);
        return dataFormatada;
    }
}
