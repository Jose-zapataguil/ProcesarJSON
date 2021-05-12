import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/** @file LeerJSON.java
 *  @class LeerJSON
 *  @brief Representa la clase que se va a encargar de obtener los jobs de uno de los ficheros cuya ruta está en config.json,
 *  que tiene un constructor al cual se le pasa como argumento un String, que representa la ruta de entrada para ese fichero
 */

public class LeerJSON {
    private FileInputStream in;
    LeerJSON(String rutaEntrada) {
        try {
            this.in = new FileInputStream(rutaEntrada);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * @brief La función obtenerElemento se encarga de obtener de uno de los archivos contemplados en config.json, obtenido
     * a partir de su ruta de entrada, un array con los jobs que hay en ese archivo.
     *
     * @return El array de elementos JSON con todos los objetos que contiene el archivo para poder procesarlo.
     */

    public JSONArray obtenerElemento(){
        JSONTokener tokener = new JSONTokener(this.in);
        JSONObject obj = new JSONObject(tokener);
        JSONObject data = (JSONObject) obj.get("data");
        JSONArray array = (JSONArray) data.get("simpleJobs");
        return array;
    }
}
