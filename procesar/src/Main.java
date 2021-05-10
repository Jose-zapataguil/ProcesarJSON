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

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                String uuaa = object.getString("uuaa");
                String rutaEntrada = object.getString("entrada");
                String rutaSalida = object.getString("salida");
                String releasekirby = object.getString("releasekirby");
                String versionkirby = object.getString("versionkirby");
                String artifactUrl = object.getString("artifactUrl");
                String extraLibs = object.getString("extraLibs");
                String releasehammurabi = object.getString("releasehammurabi");
                String versionhammurabi = object.getString("versionhammurabi");

                ProcesarJSON pj = new ProcesarJSON(rutaEntrada, uuaa, rutaSalida,releasekirby,versionkirby,artifactUrl,extraLibs,releasehammurabi,versionhammurabi);
                pj.procesar();
                System.out.println("Ficheros creados correctamente");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
