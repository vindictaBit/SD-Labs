import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class CreditCardServer {
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099);
            CreditCardService service = new CreditCardServiceImpl();
            Naming.rebind("rmi://localhost/CreditCardService", service);
            System.out.println("Servidor RMI iniciado...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
