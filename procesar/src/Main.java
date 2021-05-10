import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        iniConfig();
        /*
        Scanner sc = new Scanner(System.in);
        String rutaEntrada;
        String rutaSalida;
        String uuaa = "";
        String versionKirby = "";
        String versionHammurabi = "";
        if (args.length == 2) {
            rutaEntrada = args[0];
            rutaSalida = args[1];
        } else {
            throw new Exception("Parametros no especificados");
        }
        System.out.println("¿Desea modificar la version de Kirby? (Y/N)");
        String resp = sc.next();
        if (resp.equalsIgnoreCase("Y")) {
            System.out.println("Version Kirby:");
            versionKirby = sc.next();
        }
        System.out.println("¿Desea modificar la version de Hammurabi? (Y/N)");
        resp = sc.next();
        if (resp.equalsIgnoreCase("Y")) {
            System.out.println("Version Hammurabi:");
            versionHammurabi = sc.next();
        }
        try {
            uuaa = rutaEntrada.substring(rutaEntrada.lastIndexOf("\\") + 1);
            uuaa = uuaa.substring(uuaa.indexOf(".") + 1, uuaa.indexOf(".", uuaa.indexOf(".") + 1));
            File directorio = new File(rutaSalida + "/" + uuaa);
            if (!directorio.exists()) {
                if (directorio.mkdirs()) {
                    System.out.println("Directorio creado");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ProcesarJSON pj = new ProcesarJSON(rutaEntrada, uuaa, rutaSalida);
        pj.procesar();
        System.out.println("Ficheros creados correctamente");
        */

    }

    public static String rutaConfig(String oldRuta, String version, String uuaa) {
        String release = "spark-global-libs-release-local";
        int index1 = oldRuta.lastIndexOf("/", oldRuta.indexOf("/" + uuaa) - 1) + 1;
        String newRuta = oldRuta.substring(0, oldRuta.indexOf("artifactory/") + 12);
        int index2 = oldRuta.indexOf("/", (oldRuta.indexOf("/artifactory/") + 12) + 1);
        newRuta += release + oldRuta.substring(index2, index1) + version + "/" + oldRuta.substring(oldRuta.indexOf(uuaa));
        return newRuta;
    }

    public static String[] iniConfig() {
        String[] valores = new String[6];
        try {
            FileInputStream in = new FileInputStream("./config.json");

            JSONTokener tokener = new JSONTokener(in);
            JSONArray jsonArray = new JSONArray(tokener);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                String uuaa = object.getString("uuaa");
                System.out.println(uuaa);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return valores;

    }
}
