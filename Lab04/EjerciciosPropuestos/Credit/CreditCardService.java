import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CreditCardService extends Remote {
    void registrarTarjeta(String numeroTarjeta, double lineaDeCredito) throws RemoteException;
    double getSaldo(String numeroTarjeta) throws RemoteException;
    double getLineaDeCredito(String numeroTarjeta) throws RemoteException;
    void hacerPago(String numeroTarjeta, double monto) throws RemoteException, SaldoInsuficienteException;
    void hacerAbono(String numeroTarjeta, double monto) throws RemoteException, LineaDeCreditoExcedidaException;
    String generarNumeroTarjeta() throws RemoteException;
}
