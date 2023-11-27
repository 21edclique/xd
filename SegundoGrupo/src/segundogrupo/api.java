/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package segundogrupo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Marco
 */
public class api {

    ArrayList<Estudiante> array = new ArrayList<>();

    public ArrayList<Estudiante> GET() throws IOException {
        try {
            array.clear();
            URL url = new URL("http://localhost:8081/Quinto/api.php");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.connect();

            int response = http.getResponseCode();
            if (response != 200) {
                System.out.println("Error");
            } else {
                StringBuilder datos = new StringBuilder();
                Scanner sc = new Scanner(http.getInputStream());
                while (sc.hasNext()) {
                    datos.append(sc.next());

                }
                sc.close();

                JSONArray json = new JSONArray(datos.toString());
                for (int i = 0; i < json.length(); i++) {
                    JSONObject objeto = json.getJSONObject(i);
                    String cedula = objeto.getString("cedula");
                    String nombre = objeto.getString("nombre");
                    String apellido = objeto.getString("apellido");
                    String direccion = objeto.getString("direccion");
                    String telefono = objeto.getString("telefono");
                    Estudiante es = new Estudiante(cedula, nombre, apellido, direccion, telefono);
                    array.add(es);
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
            return null;
        }
        return array;
    }

    public void POST(String cedula, String nombre, String apellido, String direccion, String telefono) {
        try {
            array.clear();
            HttpClient cliente = HttpClientBuilder.create().build();
            String url = "http://localhost:8081/Quinto/api.php";
            HttpPost post = new HttpPost(url);
            ArrayList<BasicNameValuePair> parametros = new ArrayList<BasicNameValuePair>();
            parametros.add(new BasicNameValuePair("cedula", cedula));
            parametros.add(new BasicNameValuePair("nombre", nombre));
            parametros.add(new BasicNameValuePair("apellido", apellido));
            parametros.add(new BasicNameValuePair("direccion", direccion));
            parametros.add(new BasicNameValuePair("telefono", telefono));
            post.setEntity(new UrlEncodedFormEntity(parametros));
            cliente.execute(post);
        } catch (Exception ex) {
            System.out.println(ex);
        }

    }

    public void Actualizar(String cedula, String nombre, String apellido, String direccion, String telefono) {
        try {
            String url = "http://localhost:8081/Quinto/api.php?";
            String parametros = "cedula=" + cedula + "&nombre=" + nombre + "&apellido=" + apellido + "&direccion=" + direccion
                    + "&telefono=" + telefono;
            URL urll = new URL(url + parametros);
            HttpURLConnection conn = (HttpURLConnection) urll.openConnection();
            conn.setRequestMethod("PUT");
            int respuesta = conn.getResponseCode();
            if (respuesta == 200) {
                JOptionPane.showMessageDialog(null, "ok");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public void Eliminar(String cedula) {
        try {
            String url2 = "http://localhost:8081/Quinto/api.php";
            URL url = new URL(url2 + "?cedula=" + cedula);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("DELETE");
            int respuesta = con.getResponseCode();
            if (respuesta == 200) {
                JOptionPane.showMessageDialog(null, "ok");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public ArrayList<Estudiante> buscarPorCedula(String cedula, String nombre) {
        try {
            // Establecer la URL de la API y los parámetros de búsqueda
            String url = "http://localhost:8081/Quinto/api.php?cedula=" + cedula + "&nombre=" + nombre;

            // Crear una conexión HTTP utilizando HttpURLConnection
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");

            // Obtener la respuesta de la API
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Leer la respuesta de la API
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Procesar la respuesta y obtener los resultados
                //List<Estudiante> resultados = new ArrayList<>();
                JSONArray json = new JSONArray(response.toString());
                for (int i = 0; i < json.length(); i++) {
                    JSONObject objeto = json.getJSONObject(i);
                    cedula = objeto.getString("cedula");
                    nombre = objeto.getString("nombre");
                    String apellido = objeto.getString("apellido");
                    String direccion = objeto.getString("direccion");
                    String telefono = objeto.getString("telefono");
                    Estudiante es = new Estudiante(cedula, nombre, apellido, direccion, telefono);
                    array.add(es);
                }

                // Mostrar los resultados en la consola
                return array;

            } else {
                System.out.println("Error en la conexión: " + responseCode);
                return null;
            }
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
