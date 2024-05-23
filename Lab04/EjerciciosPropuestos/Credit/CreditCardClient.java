import java.rmi.Naming;
import java.util.Scanner;

public class CreditCardClient {
    public static void main(String[] args) {
        try {
            CreditCardService service = (CreditCardService) Naming.lookup("rmi://localhost/CreditCardService");
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("Seleccione una opción:");
                System.out.println("1. Ingresar número de tarjeta");
                System.out.println("2. Crear tarjeta");
                System.out.println("3. Salir");

                int opcion = scanner.nextInt();
                scanner.nextLine();  // Consume the newline

                switch (opcion) {
                    case 1:
                        System.out.print("Ingrese el número de su tarjeta: ");
                        String numeroTarjeta = scanner.nextLine();
                        mostrarInformacionTarjeta(service, numeroTarjeta);
                        gestionarTarjeta(service, numeroTarjeta, scanner);
                        break;
                    case 2:
                        String nuevaTarjeta = service.generarNumeroTarjeta();
                        System.out.print("Ingrese la línea de crédito para la nueva tarjeta: ");
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
                        System.out.println("Opción no válida.");
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
            System.out.println("Información de la tarjeta:");
            System.out.println("Línea de crédito: " + lineaDeCredito);
            System.out.println("Saldo disponible: " + saldo);
        } catch (Exception e) {
            System.out.println("Error al obtener la información de la tarjeta.");
            e.printStackTrace();
        }
    }

    private static void gestionarTarjeta(CreditCardService service, String numeroTarjeta, Scanner scanner) {
        while (true) {
            System.out.println("Seleccione una opción:");
            System.out.println("1. Hacer un pago");
            System.out.println("2. Hacer un abono");
            System.out.println("3. Volver al menú principal");

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
                    mostrarInformacionTarjeta(service, numeroTarjeta);  // Mostrar información actualizada
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
                    mostrarInformacionTarjeta(service, numeroTarjeta);  // Mostrar información actualizada
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }
}
