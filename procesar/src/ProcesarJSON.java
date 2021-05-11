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
    private JSONObject json;
    private JSONObject params;

    ProcesarJSON(String[] valores) {
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
        this.json = new JSONObject();
        this.params = new JSONObject();
    }

    public void procesar() {
        for (Object o : array) {
            boolean crear = true;
            JSONObject objeto = (JSONObject) o;
            String _id = (String) objeto.get("_id");
            this.json.put("_id", _id);
            this.json.put("description", objeto.get("description"));
            JSONObject config = (JSONObject) objeto.get("configuration");
            this.json.put("size", config.get("size"));
            JSONObject runtime = (JSONObject) objeto.get("runtime");
            String tipo = (String) runtime.get("id");
            JSONObject old_params = (JSONObject) config.get("params");
            if (tipo.equals("spark")) {
                String configUrl = old_params.get("configUrl").toString();
                crear = procesarKirby(configUrl, runtime, old_params, _id);
            } else if (tipo.equals("hammurabi")) {
                String configUrl = old_params.get("configUrl").toString();
                crear = procesarHammurabi(configUrl, runtime, old_params, _id);
            } else {
                procesarHDFS(runtime);
            }
            if (crear) {
                json.put("streaming", false);
                json.put("params", this.params);
                json.put("env", new JSONObject());
                json.put("kind", "processing");
                gf.CrearFichero(tipo, this.rutaSalida, this.uuaa, _id, json.toString(4));
            }
        }
        this.gf.cerrarLog();
    }

    private boolean procesarKirby(String configUrl, JSONObject runtime, JSONObject old_params, String _id) {
        boolean crear = true;
        if (configUrl.contains(this.uuaa)) {
            this.json.put("runtime", runtime.get("id") + ":" + runtime.get("version"));
            if (old_params.has("extraLibs")) {
                this.params = obtenerParams(old_params);
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
                this.json.put("tags", tags);
                if (!this.versionkirby.isEmpty()) {
                    String val = rutaConfig(old_params.get("configUrl").toString(), this.versionkirby, this.uuaa, this.releasekirby);
                    this.params.put("configUrl", val);
                }
            } else {
                this.gf.crearLogNoExtra(_id);
                crear = false;
            }
        } else {
            this.gf.crearLogNormal(_id, this.uuaa);
            crear = false;
        }
        return crear;
    }

    private boolean procesarHammurabi(String configUrl, JSONObject runtime, JSONObject old_params, String _id) {
        boolean crear = true;
        if (configUrl.contains(this.uuaa)) {
            json.put("runtime", runtime.get("id"));
            this.params = old_params;
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
            this.gf.crearLogNormal(_id, this.uuaa);
            crear = false;
        }
        return crear;
    }

    private void procesarHDFS(JSONObject runtime) {
        json.put("runtime", runtime.get("id"));
        json.put("tags", new JSONArray());
    }

    private static String rutaConfig(String oldRuta, String version, String uuaa, String release) {
        String newRuta = oldRuta.substring(0, oldRuta.indexOf("artifactory/") + 12);
        int index1 = oldRuta.lastIndexOf("/", oldRuta.indexOf("/" + uuaa) - 1) + 1;
        int index2 = oldRuta.indexOf("/", (oldRuta.indexOf("/artifactory/") + 12) + 1);
        String ult = oldRuta.substring(oldRuta.indexOf(uuaa));
        if (version.equalsIgnoreCase("NA") && release.equalsIgnoreCase("NA")) {
            newRuta = oldRuta;
        } else if (release.equalsIgnoreCase("NA") && !version.equalsIgnoreCase("NA")) {
            newRuta = oldRuta.substring(0, index1) + version + "/" + ult;
        } else if (!release.equalsIgnoreCase("NA") && version.equalsIgnoreCase("NA")) {
            newRuta += release + oldRuta.substring(index2);
        } else {
            newRuta += release + oldRuta.substring(index2, index1) + version + "/" + ult;
        }
        return newRuta;
    }

    private JSONObject obtenerParams(JSONObject old_params) {
        JSONObject param = new JSONObject();
        if (!param.has("sparkHistoryEnabled")) {
            param.put("sparkHistoryEnabled", true);
        } else {
            param.put("sparkHistoryEnabled", old_params.get("sparkHistoryEnabled"));
        }
        param.put("mainClass", old_params.get("mainClass"));
        if (this.artifactUrl.equalsIgnoreCase("NA")) {
            param.put("artifactUrl", old_params.get("artifactUrl"));
        } else {
            param.put("artifactUrl", this.artifactUrl);
        }
        if (this.extraLibs.equalsIgnoreCase("NA")) {
            param.put("extraLibs", old_params.get("extraLibs"));
        } else {
            param.put("extraLibs", this.extraLibs);
        }
        return param;
    }
}
