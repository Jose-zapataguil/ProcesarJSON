import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.*;

/** @mainpage
 *  @file Main.java
 *  @date Lunes, 10 de mayo, 2021
 *  @authors Pedro, Jose Manuel
 *  @version 1
 *  @brief Representa la clase Main, desde la cual se obtiene como parámetro de entrada la ruta del fichero de configuración.
 *  Del fichero de configuración se obtienen los diferentes valores, donde para cada objeto del archivo de configuración se llama
 *  a procesar(), de la clase ProcesarJSON
 */

public class Main {

    public static void main(String[] args) throws Exception {
       if (args.length == 0){
           throw new Exception("Es necesario la ruta del fichero de configuración");
       }else{
           iniConfig(args[0]);
       }
    }

    /**
     * @brief La función iniConfig guarda en un array de String los valores de cada objeto que hay en el fichero de configuración, para posteriormente,
     * pasar estos valores en una instancia de la clase ProcesarJSON, y llamar a procesar, función de esta clase.
     *
     * Esta función recibe como parámetro la ruta donde se encuentra el fichero de configuración. Por cada objeto que contiene este fichero
     * añade en un array de String los valores que tiene, que son 9, para pasarlos a la clase ProcesarJSON.
     *
     * @param ruta
     */

    public static void iniConfig(String ruta) {
        try {

            FileInputStream in = new FileInputStream(ruta);
            JSONTokener tokener = new JSONTokener(in);
            JSONArray jsonArray = new JSONArray(tokener);
            String [] valores = new String[9];
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                valores[0] = object.getString("uuaa");
                valores[1] = object.getString("input");
                valores[2] = object.getString("output");
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
