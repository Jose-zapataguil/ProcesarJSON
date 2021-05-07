import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        String rutaEntrada;
        String rutaSalida;
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
            String uuaa = rutaEntrada.substring(rutaEntrada.lastIndexOf("\\") + 1);
            uuaa = uuaa.substring(uuaa.indexOf(".") + 1, uuaa.indexOf(".", uuaa.indexOf(".") + 1));
            File directorio = new File(rutaSalida + "/" + uuaa);
            if (!directorio.exists()) {
                if (directorio.mkdirs()) {
                    System.out.println("Directorio creado");
                }
            }
            FileInputStream in = new FileInputStream(rutaEntrada);
            FileWriter fw = new FileWriter(rutaSalida + "/" + uuaa + "/nocreados.log");
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw);
            JSONTokener tokener = new JSONTokener(in);
            JSONObject obj = new JSONObject(tokener);
            JSONObject data = (JSONObject) obj.get("data");
            JSONArray array = (JSONArray) data.get("simpleJobs");
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
                    if (configUrl.contains(uuaa)) {
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
                        if (!versionKirby.isEmpty()) {
                            String val = rutaConfig(params.get("configUrl").toString(), versionKirby, uuaa);
                            params.put("configUrl", val);
                        }
                    } else {
                        out.println("El siguiente JSON no se ha creado por que la uuaa " + uuaa + " no coincide con la de configUrl ");
                        out.println("***" + _id + "***");
                        crear = false;
                    }
                } else if (tipo.equals("hammurabi")) {
                    String configUrl = params.get("configUrl").toString();
                    if (configUrl.contains(uuaa)) {
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
                        if (!versionHammurabi.isEmpty()) {
                            String val = rutaConfig(params.get("configUrl").toString(), versionHammurabi, uuaa);
                            params.put("configUrl", val);
                        }
                    } else {
                        out.println("El siguiente JSON no se ha creado por que la uuaa " + uuaa + " no coincide con la de configUrl");
                        out.println("***" + _id + "***");
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
                    FileWriter f;
                    if (tipo.equals("hdfs")) {
                        File dir = new File(rutaSalida + "/" + uuaa + "/hdfs/");
                        if (!dir.exists()) {
                            dir.mkdirs();
                        }
                        f = new FileWriter(dir.getPath() + "/" + _id + ".json");
                    } else {
                        f = new FileWriter(rutaSalida + "/" + uuaa + "/" + _id + ".json");
                    }
                    f.write(json.toString(4));
                    f.close();
                }
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Ficheros creados correctamente");
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
