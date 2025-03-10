package com.gym.proyecto.utilidades;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseJson {

  public static ResponseEntity<Object> generateResponse(HttpStatus status, String mensaje) {
    Map<String, Object> map = new HashMap<>();
        try {
            map.put("status", status.value());//Muestra el del estado http
            map.put("mensaje", mensaje); //Muestra el mensaje
            return new ResponseEntity<Object>(map, status);

        } catch (Exception e) {
            map.clear();
            map.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());//Muestra el error del estado http
            map.put("mensaje", e.getMessage());
            return new ResponseEntity<Object>(map, status);
        }
    }

    public static ResponseEntity<Object> generateResponseObject(HttpStatus status, Object data) {
        Map<String, Object> map = new HashMap<>();
        try {
            map.put("status", status.value()); // Código de estado HTTP
            map.put("data", data); // El objeto que se pasa como respuesta
            return new ResponseEntity<>(map, status);
        } catch (Exception e) {
            map.clear();
            map.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            map.put("mensaje", e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
