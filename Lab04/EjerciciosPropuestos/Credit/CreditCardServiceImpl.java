import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

class Tarjeta {
    double saldo;
    double lineaDeCredito;

    Tarjeta(double lineaDeCredito) {
        this.saldo = lineaDeCredito;
        this.lineaDeCredito = lineaDeCredito;
    }
}

public class CreditCardServiceImpl extends UnicastRemoteObject implements CreditCardService {
    private Map<String, Tarjeta> tarjetas;

    protected CreditCardServiceImpl() throws RemoteException {
        super();
        tarjetas = new HashMap<>();
    }

    @Override
    public void registrarTarjeta(String numeroTarjeta, double lineaDeCredito) throws RemoteException {
        if (!tarjetas.containsKey(numeroTarjeta)) {
            Tarjeta tarjeta = new Tarjeta(lineaDeCredito);
            tarjetas.put(numeroTarjeta, tarjeta);
            System.out.println("Tarjeta registrada: " + numeroTarjeta + " con línea de crédito: " + lineaDeCredito);
        } else {
            System.out.println("La tarjeta ya está registrada: " + numeroTarjeta);
        }
    }

    @Override
    public double getSaldo(String numeroTarjeta) throws RemoteException {
        Tarjeta tarjeta = tarjetas.get(numeroTarjeta);
        if (tarjeta != null) {
            return tarjeta.saldo;
        }
        return 0.0;
    }

    @Override
    public double getLineaDeCredito(String numeroTarjeta) throws RemoteException {
        Tarjeta tarjeta = tarjetas.get(numeroTarjeta);
        if (tarjeta != null) {
            return tarjeta.lineaDeCredito;
        }
        return 0.0;
    }

    @Override
    public void hacerPago(String numeroTarjeta, double monto) throws RemoteException, SaldoInsuficienteException {
        Tarjeta tarjeta = tarjetas.get(numeroTarjeta);
        if (tarjeta != null && monto > 0) {
            if (tarjeta.saldo >= monto) {
                tarjeta.saldo -= monto;
                System.out.println("Pago realizado: " + monto);
            } else {
                throw new SaldoInsuficienteException("Saldo insuficiente para realizar el pago.");
            }
        } else {
            System.out.println("El monto del pago debe ser positivo o la tarjeta no está registrada.");
        }
    }

    @Override
    public void hacerAbono(String numeroTarjeta, double monto) throws RemoteException, LineaDeCreditoExcedidaException {
        Tarjeta tarjeta = tarjetas.get(numeroTarjeta);
        if (tarjeta != null && monto > 0) {
            double nuevoSaldo = tarjeta.saldo + monto;
            if (nuevoSaldo <= tarjeta.lineaDeCredito) {
                tarjeta.saldo = nuevoSaldo;
                System.out.println("Abono realizado: " + monto);
            } else {
                throw new LineaDeCreditoExcedidaException("El abono excede la línea de crédito.");
            }
        } else {
            System.out.println("El monto del abono debe ser positivo o la tarjeta no está registrada.");
        }
    }

    @Override
    public String generarNumeroTarjeta() throws RemoteException {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            sb.append(rand.nextInt(10));
        }
        return sb.toString();
    }
}
