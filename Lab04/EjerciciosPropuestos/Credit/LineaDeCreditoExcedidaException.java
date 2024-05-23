public class LineaDeCreditoExcedidaException extends Exception {
    public LineaDeCreditoExcedidaException(String message) {
        super(message);
        System.out.println(message);
    }
}
