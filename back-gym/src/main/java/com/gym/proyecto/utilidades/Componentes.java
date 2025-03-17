package com.gym.proyecto.utilidades;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class Componentes {

    public static String normalizarTexto(String texto) {
        String normalized = Normalizer.normalize(texto, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalized).replaceAll("").replace("ñ", "n").replace("Ñ", "N");
    }

}
