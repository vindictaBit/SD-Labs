import java.rmi.Naming;
import java.util.Scanner;

public class CreditCardClient {
    public static void main(String[] args) {
        try {
            CreditCardService service = (CreditCardService) Naming.lookup("rmi://localhost/CreditCardService");
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("Seleccione una opci�n:");
                System.out.println("1. Ingresar n�mero de tarjeta");
                System.out.println("2. Crear tarjeta");
                System.out.println("3. Salir");

                int opcion = scanner.nextInt();
                scanner.nextLine();  // Consume the newline

                switch (opcion) {
                    case 1:
                        System.out.print("Ingrese el n�mero de su tarjeta: ");
                        String numeroTarjeta = scanner.nextLine();
                        mostrarInformacionTarjeta(service, numeroTarjeta);
                        gestionarTarjeta(service, numeroTarjeta, scanner);
                        break;
                    case 2:
                        String nuevaTarjeta = service.generarNumeroTarjeta();
                        System.out.print("Ingrese la l�nea de cr�dito para la nueva tarjeta: ");
                        double lineaDeCredito = scanner.nextDouble();
                        scanner.nextLine();  // Consume the newline
                        service.registrarTarjeta(nuevaTarjeta, lineaDeCredito);
                        System.out.println("Nueva tarjeta creada: " + nuevaTarjeta);
                        break;
                    case 3:
                        System.out.println("Saliendo...");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Opci�n no v�lida.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void mostrarInformacionTarjeta(CreditCardService service, String numeroTarjeta) {
        try {
            double saldo = service.getSaldo(numeroTarjeta);
            double lineaDeCredito = service.getLineaDeCredito(numeroTarjeta);
            System.out.println("Informaci�n de la tarjeta:");
            System.out.println("L�nea de cr�dito: " + lineaDeCredito);
            System.out.println("Saldo disponible: " + saldo);
        } catch (Exception e) {
            System.out.println("Error al obtener la informaci�n de la tarjeta.");
            e.printStackTrace();
        }
    }

    private static void gestionarTarjeta(CreditCardService service, String numeroTarjeta, Scanner scanner) {
        while (true) {
            System.out.println("Seleccione una opci�n:");
            System.out.println("1. Hacer un pago");
            System.out.println("2. Hacer un abono");
            System.out.println("3. Volver al men� principal");

            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el monto del pago: ");
                    double pago = scanner.nextDouble();
                    try {
                        service.hacerPago(numeroTarjeta, pago);
                        System.out.println("Pago realizado exitosamente.");
                    } catch (SaldoInsuficienteException e) {
                        System.out.println(e.getMessage());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mostrarInformacionTarjeta(service, numeroTarjeta);  // Mostrar informaci�n actualizada
                    break;
                case 2:
                    System.out.print("Ingrese el monto del abono: ");
                    double abono = scanner.nextDouble();
                    try {
                        service.hacerAbono(numeroTarjeta, abono);
                        System.out.println("Abono realizado exitosamente.");
                    } catch (LineaDeCreditoExcedidaException e) {
                        System.out.println(e.getMessage());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mostrarInformacionTarjeta(service, numeroTarjeta);  // Mostrar informaci�n actualizada
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Opci�n no v�lida.");
            }
        }
    }
}
