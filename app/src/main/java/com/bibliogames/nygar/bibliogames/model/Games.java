package com.bibliogames.nygar.bibliogames.model;


import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Clase de los objetos juegos
 *
 * Propiedades:
 * id: tipo entero, contiene el identificador del juego
 * title: tipo string, contiene el titulo del juego
 * price: tipo decimal, es el precio que costo el juego
 * dateBuy: tipo fecha, dia de cuando se compro el juego
 * console: tipo consola, es la consola en la que se compro el juego
 * cover: tipo string, guarda la imagen del juego
 */

@JsonObject(serializeNullObjects=true, serializeNullCollectionElements =true )
public class Games implements Parcelable {
    @JsonField(name = "id")
    private int id;
    @JsonField(name = "titulo")
    private String title;
    @JsonField
    private Drawable cover;
    @JsonField(name = "precio_compra")
    private float price;
    @JsonField(name = "fecha_compra")
    private String dateBuy;
    @JsonField(name = "consola")
    private Console console;

    public Games() {
    }

    public Games(int id, String title, float price, Console console) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.console = console;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Drawable getCover() {
        return cover;
    }

    public void setCover(Drawable cover) {
        this.cover = cover;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDateBuy() {
        return dateBuy;
    }

    public void setDateBuy(String dateBuy) {
        this.dateBuy = dateBuy;
    }

    public Console getConsole() {
        return console;
    }

    public void setConsole(Console console) {
        this.console = console;
    }

    /**
     * Metdodo para saber si dos juegos son iguales usando como criterio el id
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        boolean res =false;
        if(obj instanceof Games && ((Games) obj).id==id){
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
        dest.writeString(this.title);
        dest.writeFloat(this.price);
        dest.writeString(this.dateBuy);
        dest.writeParcelable(this.console, flags);
    }

    protected Games(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.price = in.readFloat();
        this.dateBuy = in.readString();
        this.console = in.readParcelable(Console.class.getClassLoader());
    }

    public static final Creator<Games> CREATOR = new Creator<Games>() {
        @Override
        public Games createFromParcel(Parcel source) {
            return new Games(source);
        }

        @Override
        public Games[] newArray(int size) {
            return new Games[size];
        }
    };
}
