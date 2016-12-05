package com.bibliogames.nygar.bibliogames.view.utils;

import android.content.Context;

import com.bibliogames.nygar.bibliogames.model.Console;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que servira para parsear el XML de consolas
 */

public class ParseXML {

    /**
     * LeerXML
     * ******************************
     * Descripcion: lee el XML de las consolas
     * Entradas: un context, para encontrar el fichero XML
     * Salidas: un ArrayList con las consoals del XML
     * Precondiciones: debe ser un contexto valido en donde se pueda hacer getResources
     * Postcondiciones: se devolvera un array con las consolas que haya en el XML para meterlo en un LIST
     *
     * @param context
     * @return
     */

    public List<Console> leerXML(Context context){
        //Vamos a parsear el XML
        XmlPullParserFactory pullParserFactory;
        int event;
        List<Console> consoles = new ArrayList<>();
        Console console= new Console();
        String text = "";

        try{
            pullParserFactory=XmlPullParserFactory.newInstance(); //Es un manager para el XmlPullParser
            XmlPullParser parser= pullParserFactory.newPullParser(); //Podriamos hacerlo usandoxm.newpullparser tambien

            //Declaramos el xml
            InputStream file = context.getResources().getAssets().open("consoleList.xml");

            parser.setInput(file, null);
            event = parser.getEventType();

            while(event !=XmlPullParser.END_DOCUMENT){
                String etiqueta = parser.getName();

                switch(event){
                    case XmlPullParser.START_TAG:
                        if(etiqueta.equalsIgnoreCase("console")) {
                            console = new Console();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        text=parser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if(etiqueta.equalsIgnoreCase("console")){
                            consoles.add(console);
                        }else if(etiqueta.equalsIgnoreCase("id")){
                            console.setId(Integer.parseInt(text));
                        }else if(etiqueta.equalsIgnoreCase("name")){
                            console.setName(text);
                        }
                        break;

                }//Finswitch
                event = parser.next();
            }//finwhile

        }catch (IOException|XmlPullParserException e){
            e.printStackTrace();
        }
        return consoles;
    }//FinFuncion
}
