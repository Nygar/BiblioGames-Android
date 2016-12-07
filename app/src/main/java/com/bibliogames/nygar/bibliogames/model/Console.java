package com.bibliogames.nygar.bibliogames.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.Date;

/**
 * Clase del objeto consola
 *
 * Propiedades
 * ******************
 * id: tipo entero, recoge el identificador de la consola
 * name: tipo string, contiene el nombre de la consola
 * dateFabrication: tipo fecha, contiene la fecha de fabricacion de la consola
 * logo: tipo string, se usa para guardar la ruta de la imagen de la consola
 */

@JsonObject(serializeNullObjects=true, serializeNullCollectionElements =true )
public class Console implements Parcelable {

    @JsonField(name = "console_id")
    private int id;
    @JsonField(name = "console_name")
    private String name;
    @JsonField(name = "console_dateRelease")
    private Date dateFabrication;
    private String logo;

    public Console() {
    }

    public Console(String name) {
        this.name = name;
    }

    public Console(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateFabrication() {
        return dateFabrication;
    }

    public void setDateFabrication(Date dateFabrication) {
        this.dateFabrication = dateFabrication;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    /**
     * Metdodo para saber si dos consolas son iguales usando como criterio el id
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        boolean res =false;
        if(obj instanceof Console && ((Console) obj).id==id){
            res =true;
        }
        return res;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeLong(this.dateFabrication != null ? this.dateFabrication.getTime() : -1);
        dest.writeString(this.logo);
    }

    protected Console(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        long tmpDateFabrication = in.readLong();
        this.dateFabrication = tmpDateFabrication == -1 ? null : new Date(tmpDateFabrication);
        this.logo = in.readString();
    }

    public static final Parcelable.Creator<Console> CREATOR = new Parcelable.Creator<Console>() {
        @Override
        public Console createFromParcel(Parcel source) {
            return new Console(source);
        }

        @Override
        public Console[] newArray(int size) {
            return new Console[size];
        }
    };
}
