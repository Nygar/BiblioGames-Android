package com.bibliogames.nygar.bibliogames.model;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Clase para los objetos de la grafica
 *
 * Propiedades:
 * *********************
 * title: guarda el nombre de la consola
 * entryData: guarda lo que se ha gastado en esa consola
 */

@JsonObject(serializeNullObjects=true, serializeNullCollectionElements =true )
public class GraphicEntry {
    @JsonField(name = "name_console")
    private String entryTittle;
    @JsonField(name = "gastado")
    private float entryData;

    public GraphicEntry() {
    }

    public GraphicEntry(String entryTittle, int entryData) {
        this.entryTittle = entryTittle;
        this.entryData = entryData;
    }

    public String getEntryTittle() {
        return entryTittle;
    }

    public void setEntryTittle(String entryTittle) {
        this.entryTittle = entryTittle;
    }

    public float getEntryData() {
        return entryData;
    }

    public void setEntryData(float entryData) {
        this.entryData = entryData;
    }
}
