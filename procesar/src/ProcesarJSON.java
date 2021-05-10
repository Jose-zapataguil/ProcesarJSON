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

    ProcesarJSON(String rutaEntrada, String uuaa, String rutaSalida,String releasekirby,String versionkirby,String artifactUrl,String extraLibs,String releasehammurabi,String versionhammurabi) {
        LeerJSON lj = new LeerJSON(rutaEntrada);
        this.array = lj.obtenerElemento();
        this.uuaa = uuaa;
        this.rutaSalida = rutaSalida;
        this.gf = new GenerarFicheros(rutaSalida, uuaa);
        this.releasekirby = releasekirby;
        this.versionkirby = versionkirby;
        this.artifactUrl = artifactUrl;
        this.extraLibs = extraLibs;
        this.releasehammurabi = releasehammurabi;
        this.versionhammurabi = versionhammurabi;

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
            JSONObject params = (JSONObject) config.get("params");
            if (tipo.equals("spark")) {
                String configUrl = params.get("configUrl").toString();
                if (configUrl.contains(this.uuaa)) {
                    json.put("runtime", runtime.get("id") + ":" + runtime.get("version"));
                    params.keySet().remove("repoSecurity");
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
//                    if (!versionKirby.isEmpty()) {
//                        String val = rutaConfig(params.get("configUrl").toString(), versionKirby, uuaa);
//                        params.put("configUrl", val);
//                    }
                } else {
                    this.gf.crearLog(_id, this.uuaa);
                    crear = false;
                }
            } else if (tipo.equals("hammurabi")) {
                String configUrl = params.get("configUrl").toString();
                if (configUrl.contains(this.uuaa)) {
                    json.put("runtime", runtime.get("id"));
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
//                    if (!versionHammurabi.isEmpty()) {
//                        String val = rutaConfig(params.get("configUrl").toString(), versionHammurabi, uuaa);
//                        params.put("configUrl", val);
//                    }
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

    public static String rutaConfig(String oldRuta, String version, String uuaa) {
        String release = "spark-global-libs-release-local";
        int index1 = oldRuta.lastIndexOf("/", oldRuta.indexOf("/" + uuaa) - 1) + 1;
        String newRuta = oldRuta.substring(0, oldRuta.indexOf("artifactory/") + 12);
        int index2 = oldRuta.indexOf("/", (oldRuta.indexOf("/artifactory/") + 12) + 1);
        newRuta += release + oldRuta.substring(index2, index1) + version + "/" + oldRuta.substring(oldRuta.indexOf(uuaa));
        return newRuta;
    }
}
