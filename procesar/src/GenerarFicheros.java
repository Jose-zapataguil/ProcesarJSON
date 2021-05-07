import java.io.*;

public class GenerarFicheros {
    private FileWriter fw;
    private BufferedWriter bw;
    private PrintWriter out;

    GenerarFicheros(String rutaSalida, String uuaa) {
        try {
            this.fw = new FileWriter(rutaSalida + "/" + uuaa + "/nocreados.log");
            this.bw = new BufferedWriter(fw);
            this.out = new PrintWriter(bw);
            File directorio = new File(rutaSalida + "/" + uuaa);
            if (!directorio.exists()) {
                if (directorio.mkdirs()) {
                    System.out.println("Directorio creado");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    public void crearLog(String _id, String uuaa) {
        out.println("El siguiente JSON no se ha creado por que la uuaa " + uuaa + " no coincide con la de configUrl");
        out.println("***" + _id + "***");

    }

    public void cerrarLog(){
        out.close();
    }
}
