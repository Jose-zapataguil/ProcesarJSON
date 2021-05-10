import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
       iniConfig();



    }


    public static void iniConfig() {
        try {

            FileInputStream in = new FileInputStream("./config.json");
            JSONTokener tokener = new JSONTokener(in);
            JSONArray jsonArray = new JSONArray(tokener);
            String [] valores = new String[9];
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                valores[0] = object.getString("uuaa");
                valores[1] = object.getString("entrada");
                valores[2] = object.getString("salida");
                valores[3] = object.getString("releasekirby");
                valores[4] = object.getString("versionkirby");
                valores[5] = object.getString("artifactUrl");
                valores[6] = object.getString("extraLibs");
                valores[7] = object.getString("releasehammurabi");
                valores[8] = object.getString("versionhammurabi");

                ProcesarJSON pj = new ProcesarJSON(valores);
                pj.procesar();
                System.out.println("Ficheros creados correctamente");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
