import java.io.*;
import java.net.*;

public class CristianClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345); // Conexión al servidor en localhost, puerto 12345

            // Streams de entrada y salida para la comunicación con el servidor
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Solicitar el tiempo al servidor
            out.println("TIME_REQUEST");

            // Leer el tiempo del servidor y ajustar el tiempo local
            String serverTime = in.readLine();
            long adjustedTime = Long.parseLong(serverTime);
            System.out.println("Tiempo del servidor: " + adjustedTime);

            // Cerrar los streams y el socket
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
