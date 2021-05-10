import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class LeerJSON {
    private FileInputStream in;
    LeerJSON(String rutaEntrada) {
        try {
            this.in = new FileInputStream(rutaEntrada);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public JSONArray obtenerElemento(){
        JSONTokener tokener = new JSONTokener(this.in);
        JSONObject obj = new JSONObject(tokener);
        JSONObject data = (JSONObject) obj.get("data");
        JSONArray array = (JSONArray) data.get("simpleJobs");
        return array;
    }
}
