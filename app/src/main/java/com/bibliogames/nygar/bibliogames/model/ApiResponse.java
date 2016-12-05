package com.bibliogames.nygar.bibliogames.model;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

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
