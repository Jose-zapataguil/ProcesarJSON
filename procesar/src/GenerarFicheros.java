import java.io.*;

/** @file GenerarFicheros.java
 *  @class GenerarFicheros
 *  @brief Representa la clase que se va a encargar de generar los ficheros con la información extraída o el fichero log, que se creará
 *  cuando los jobs no tengan uuaa o el parámetro "extralibs".
 *
 *  Tiene un constructor al cual se le pasa como argumento dos String,que representan la ruta de salida y la uuaa para el fichero con los jobs
 *  procesado (obtenido de config.json), para obtener una ruta final donde crear tanto los fichero con información como el fichero log.
 */

public class GenerarFicheros {
    private FileWriter fw;
    private BufferedWriter bw;
    private PrintWriter out;


    GenerarFicheros(String rutaSalida, String uuaa) {
        try {
            File directorio = new File(rutaSalida + "/" + uuaa);
            if (!directorio.exists()) {
                if (directorio.mkdirs()) {
                    System.out.println("Directorio creado");
                }
            }
            this.fw = new FileWriter(rutaSalida + "/" + uuaa + "/nocreados.log");
            this.bw = new BufferedWriter(fw);
            this.out = new PrintWriter(bw);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @brief La función CrearFichero crea el fichero con la información extraída de cada job en procesar de la clase ProcesarJSON.
     *
     *Según lo que haya en el id del runtime, la ruta de salida para el fichero será distinta.
     *
     * @param tipo
     * @param rutaSalida
     * @param uuaa
     * @param _id
     * @param json
     */

    public void CrearFichero(String tipo, String rutaSalida, String uuaa, String _id, String json) {
        String ruta;
        if (tipo.equals("hdfs")) {
            ruta = rutaSalida + "/" + uuaa + "/hdfs/";
            File dir = new File(ruta);
            if (!dir.exists()) {
                dir.mkdirs();
            }
        } else {
            ruta = rutaSalida + "/" + uuaa + "/";
        }
        try {
            FileWriter f = new FileWriter(ruta + _id + ".json");
            f.write(json);
            f.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @brief La función crearLogNormal se encarga de escribir en el fichero nocreados.log el id del JSON cuando la uuaa que hay en configUrl de ese
     * objeto no coincide con la uuaa que hay en config.json.
     *
     * @param _id
     * @param uuaa
     */

    public void crearLogNormal(String _id, String uuaa) {
        out.println("El siguiente JSON no se ha creado por que la uuaa " + uuaa + " no coincide con la de configUrl");
        out.println("***" + _id + "***");

    }

    /**
     * @brief La función crearLogNoExtra se encarga de escribir en el fichero nocreados.log el id del JSON cuando este no tiene
     * el parámetro "extraLibs".
     *
     * @param _id
     */

    public void crearLogNoExtra(String _id) {
        out.println("El siguiente JSON no se ha creado porque no continene el parametro extraLibs");
        out.println("***" + _id + "***");
    }

    /**
     * @brief La función cerrarLog se encarga de cerrar el archivo nocreados.log.
     */

    public void cerrarLog() {
        out.close();
    }
}
