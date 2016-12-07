package com.bibliogames.nygar.bibliogames.presenter.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class BitmapEncode {

    /**
     * Encode to BAse 64
     * ********************************
     * Descripcion: Metodo que codifica un bitmap en un string de Base 64
     * Entradas: un Bitmap
     * Salidas: un String
     * Precondiciones: el bitmap no debe ser null
     * Postcondiciones: el String es una codificacion en base 64 del bitmap
     *
     * CODIFICAR LOS BITMAP EN BASE 64 SIRVE PARA PODERLOS ENVIAR AL HOST
     * Y ALLI DECODIFICARLO Y PODER GRABAR LA IMAGEN
     *
     * @param image
     * @return
     */
    public String encodeTobase64(Bitmap image)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();

        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    /**
     * decode to BAse 64
     * ********************************
     * Descripcion: Metodo que decodifica un string de Base 64 a un bitmap
     * Entradas: un String
     * Salidas: un Bitmap
     * Precondiciones: el String debe ser una codificacion en abse 64
     * Postcondiciones: el bitmap formado por el string codificado
     *
     *
     */
    public Bitmap decodeBase64(String input)
    {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }
}
