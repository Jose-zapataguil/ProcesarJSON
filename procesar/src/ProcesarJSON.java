import org.json.JSONArray;
import org.json.JSONObject;


public class ProcesarJSON {
    private JSONArray array;
    private String uuaa;
    private String rutaSalida;
    private GenerarFicheros gf;
    private String releasekirby;
    private String versionkirby;
    private String artifactUrl;
    private String extraLibs;
    private String releasehammurabi;
    private String versionhammurabi;

    ProcesarJSON(String [] valores) {
        LeerJSON lj = new LeerJSON(valores[1]);
        this.array = lj.obtenerElemento();
        this.uuaa = valores[0];
        this.rutaSalida = valores[2];
        this.gf = new GenerarFicheros(rutaSalida, uuaa);
        this.releasekirby = valores[3];
        this.versionkirby = valores[4];
        this.artifactUrl = valores[5];
        this.extraLibs = valores[6];
        this.releasehammurabi = valores[7];
        this.versionhammurabi = valores[8];

    }

    public void procesar() {
        for (Object o : array) {
            boolean crear = true;
            JSONObject json = new JSONObject();
            JSONObject objeto = (JSONObject) o;
            String _id = (String) objeto.get("_id");
            json.put("_id", _id);
            json.put("description", objeto.get("description"));
            JSONObject config = (JSONObject) objeto.get("configuration");
            json.put("size", config.get("size"));
            JSONObject runtime = (JSONObject) objeto.get("runtime");
            String tipo = (String) runtime.get("id");
            JSONObject old_params = (JSONObject) config.get("params");
            JSONObject params = new JSONObject();
            if (tipo.equals("spark")) {
                String configUrl = old_params.get("configUrl").toString();
                if (configUrl.contains(this.uuaa)) {
                    json.put("runtime", runtime.get("id") + ":" + runtime.get("version"));
                    params = obtenerParams(old_params);
                    JSONArray tags = new JSONArray();
                    if (_id.contains("raw")) {
                        tags.put("workday");
                        if (_id.contains("ldap")) {
                            tags.put("ldap");
                        }
                        tags.put("raw");
                        tags.put("kirby");
                    } else if (_id.contains("mastertrck")) {
                        tags.put("workday");
                        if (_id.contains("ldap")) {
                            tags.put("ldap");
                        }
                        tags.put("tracking");
                        tags.put("kirby");
                    } else if (_id.contains("master")) {
                        tags.put("workday");
                        if (_id.contains("ldap")) {
                            tags.put("ldap");
                        }
                        tags.put("master");
                        tags.put("kirby");
                    }
                    json.put("tags", tags);
                    if (!this.versionkirby.isEmpty()) {
                        String val = rutaConfig(old_params.get("configUrl").toString(), this.versionkirby, this.uuaa, this.releasekirby);
                        params.put("configUrl", val);
                    }
                } else {
                    this.gf.crearLog(_id, this.uuaa);
                    crear = false;
                }
            } else if (tipo.equals("hammurabi")) {
                String configUrl = old_params.get("configUrl").toString();
                if (configUrl.contains(this.uuaa)) {
                    json.put("runtime", runtime.get("id"));
                    params = old_params;
                    JSONArray tags = new JSONArray();
                    if (_id.contains("raw")) {
                        tags.put("workday");
                        if (_id.contains("ldap")) {
                            tags.put("ldap");
                        }
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
                    } else if (_id.contains("master")) {
                        tags.put("workday");
                        if (_id.contains("ldap")) {
                            tags.put("ldap");
                        }
                        tags.put("master");
                        tags.put("hammurabi");
                    }
                    json.put("tags", tags);
                    if (!this.versionhammurabi.isEmpty()) {
                        String val = rutaConfig(params.get("configUrl").toString(), this.versionhammurabi, this.uuaa, this.releasehammurabi);
                        params.put("configUrl", val);
                    }
                } else {
                    this.gf.crearLog(_id, this.uuaa);
                    crear = false;
                }
            } else {
                json.put("runtime", runtime.get("id"));
                json.put("tags", new JSONArray());
            }
            if (crear) {
                json.put("streaming", false);
                json.put("params", params);
                json.put("env", new JSONObject());
                json.put("kind", "processing");
                gf.CrearFichero(tipo, this.rutaSalida, this.uuaa, _id, json.toString(4));
            }
        }
        this.gf.cerrarLog();
    }

    private static String rutaConfig(String oldRuta, String version, String uuaa, String release) {
        int index1 = oldRuta.lastIndexOf("/", oldRuta.indexOf("/" + uuaa) - 1) + 1;
        String newRuta = oldRuta.substring(0, oldRuta.indexOf("artifactory/") + 12);
        int index2 = oldRuta.indexOf("/", (oldRuta.indexOf("/artifactory/") + 12) + 1);
        newRuta += release + oldRuta.substring(index2, index1) + version + "/" + oldRuta.substring(oldRuta.indexOf(uuaa));
        return newRuta;
    }

    private JSONObject obtenerParams(JSONObject old_params) {
        JSONObject params = new JSONObject();
        if (!params.has("sparkHistoryEnabled")){
            params.put("sparkHistoryEnabled",true);
        }else{
            params.put("sparkHistoryEnabled", old_params.get("sparkHistoryEnabled"));
        }
        params.put("mainClass", old_params.get("mainClass"));
        params.put("artifactUrl", this.artifactUrl);
        params.put("extraLibs", this.extraLibs);
        return params;
    }
}
