package com.bibliogames.nygar.bibliogames.model;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Clase para el objeto usuario
 * ********************************
 * id: tipo int, guarda el id del usuario
 * nick: tipo string guarda el nombre del login del usuario
 * pass: tipo string, guarda la contraseña del usuario
 * name: tipo string, guarda el nombre del usuario
 * avatar: tipo string, guarda la ruta del usuario
 */

@JsonObject(serializeNullObjects=true, serializeNullCollectionElements =true )
public class User {
    @JsonField(name = "id")
    private int id;
    @JsonField(name = "nick")
    private String nick;
    @JsonField(name = "pass")
    private String pass;
    @JsonField
    private String avatar;
    @JsonField(name = "name")
    private String name;

    public User() {
    }

    public User(String nick, String pass) {
        this.nick = nick;
        this.pass = pass;
    }

    public User(String nick, String pass, String name) {
        this.nick = nick;
        this.pass = pass;
        this.name = name;
    }

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    /**
     * Metodo para ver si dos Usuarios son iguales usando como criterio el id
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        boolean res =false;
        if(obj instanceof User && ((User) obj).id==id){
            res =true;
        }
        return res;
    }
}
