package com.bibliogames.nygar.bibliogames.model;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Clase Objeto usado para recibir las respuestas genericas de la api
 *
 * Propiedades
 * ********************
 * status: tipo entero, recoge el status de como se ha realizado el servicio y sirve para controlar si
 *          el servicio ha dado algun error
 * message: tipo string, es donde se recoge el mensaje de respuesta del servicio
 */

@JsonObject(serializeNullObjects=true, serializeNullCollectionElements =true )
public class ApiResponse {

    @JsonField(name = "response_estatus")
    private int status;
    @JsonField(name = "response_message")
    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ApiResponse() {
    }
}
