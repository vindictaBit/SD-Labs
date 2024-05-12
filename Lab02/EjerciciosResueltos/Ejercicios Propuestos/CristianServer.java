import java.io.*;
import java.net.*;
import java.util.*;

public class CristianServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345); // Crear un socket de servidor en el puerto 12345

            while (true) {
                Socket clientSocket = serverSocket.accept(); // Aceptar conexiones entrantes

                // Streams de entrada y salida para la comunicación con el cliente
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                
                // Obtener la hora actual del sistema y enviarla al cliente
                long currentTime = System.currentTimeMillis();
                out.println(currentTime);

                // Cerrar el stream y el socket del cliente
                out.close();
                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
