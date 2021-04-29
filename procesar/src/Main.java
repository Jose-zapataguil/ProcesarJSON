import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        String ruta = "C:\\Users\\admin\\Downloads\\gl.swd1.app-id-17304.dev.json";
        try {
            FileInputStream in = new FileInputStream(ruta);
            JSONTokener tokener = new JSONTokener(in);
            JSONObject obj = new JSONObject(tokener);
            JSONObject data = (JSONObject) obj.get("data");
            JSONArray array = (JSONArray) data.get("simpleJobs");
            for (Object o : array) {
                JSONObject json = new JSONObject();
                JSONObject objeto = (JSONObject) o;
                String _id = (String) objeto.get("_id");
                json.put("_id", _id);
                json.put("description", objeto.get("description"));
                JSONObject config = (JSONObject) objeto.get("configuration");
                json.put("size", config.get("size"));
                JSONObject runtime = (JSONObject) objeto.get("runtime");
                String tipo = (String) runtime.get("id");
                JSONObject params = (JSONObject) config.get("params");
                if (tipo.equals("spark")) {
                    json.put("runtime", runtime.get("id") + ":" + runtime.get("version"));
                    params.keySet().remove("repoSecurity");
                    JSONArray tags = new JSONArray();
                    if (_id.contains("raw")) {
                        tags.put("workday");
                        tags.put("raw");
                        tags.put("kirby");
                    } else if (_id.contains("mastertrck")) {
                        tags.put("workday");
                        if (_id.contains("ldap")) {
                            tags.put("ldap");
                            tags.put("tracking");
                            tags.put("kirby");
                        } else {
                            tags.put("tracking");
                            tags.put("kirby");
                        }
                    } else if (_id.contains("master")) {
                        tags.put("workday");
                        if (_id.contains("ldap")) {
                            tags.put("ldap");
                            tags.put("master");
                            tags.put("kirby");
                        } else {
                            tags.put("master");
                            tags.put("kirby");
                        }
                    }
                    json.put("tags", tags);
                } else if (tipo.equals("hammurabi")) {
                    json.put("runtime", runtime.get("id"));
                    JSONArray tags = new JSONArray();
                    if (_id.contains("raw")) {
                        tags.put("workday");
                        tags.put("raw");
                        tags.put("hammurabi");
                    } else if (_id.contains("staging")) {
                        tags.put("workday");
                        tags.put("staging");
                        tags.put("hammurabi");
                    } else if (_id.contains("mastertrck")) {
                        tags.put("workday");
                        if (_id.contains("ldap")) {
                            tags.put("ldap");
                        }
                        tags.put("tracking");
                        tags.put("hammurabi");
                    }else if(_id.contains("master")){
                        tags.put("workday");
                        if (_id.contains("ldap")){
                            tags.put("ldap");
                        }
                        tags.put("master");
                        tags.put("hammurabi");
                    }
                    json.put("tags", tags);
                } else {
                    json.put("runtime", runtime.get("id"));
                    json.put("tags", new JSONArray());

                }
                json.put("streaming", false);
                json.put("params", params);
                json.put("env", new JSONObject());
                json.put("kind", "processing");
                FileWriter f = new FileWriter("salida/" + _id + ".json");
                f.write(json.toString());
                f.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Ficheros creados correctamente");
    }
}
