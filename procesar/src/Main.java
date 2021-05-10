import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.*;

public class Main {

    public static void main(String[] args) throws Exception {
       if (args.length == 0){
           throw new Exception("Es necesario la ruta del fichero de configuración");
       }else{
           iniConfig(args[0]);
       }
    }


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
