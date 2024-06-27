import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionExample {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/testdb";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    public static void main(String[] args) {
        Connection conn = null;
        try {
            // Establecer la conexión
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Deshabilitar el modo de autocommit
            conn.setAutoCommit(false);

            // Operaciones dentro de la transacción
            updateAccountBalance(conn, "user1", -500); // Retirar dinero
            updateAccountBalance(conn, "user2", 500);  // Depositar dinero

            // Confirmar la transacción
            conn.commit();
            System.out.println("Transacción completada con éxito.");

        } catch (SQLException e) {
            // Manejo de errores
            try {
                if (conn != null) {
                    conn.rollback(); // Revertir la transacción en caso de error
                    System.err.println("Transacción revertida debido a un error.");
                }
            } catch (SQLException rollbackEx) {
                System.err.println("Error al revertir la transacción: " + rollbackEx.getMessage());
            }
            System.err.println("Error durante la transacción: " + e.getMessage());
        } finally {
            // Cerrar la conexión
            try {
                if (conn != null) {
                    conn.setAutoCommit(true); // Restaurar el modo de autocommit
                    conn.close();
                }
            } catch (SQLException closeEx) {
                System.err.println("Error al cerrar la conexión: " + closeEx.getMessage());
            }
        }
    }

    // Método para actualizar el saldo de una cuenta
    private static void updateAccountBalance(Connection conn, String accountName, int amount) throws SQLException {
        String sql = "UPDATE accounts SET balance = balance + ? WHERE account_name = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, amount);
            pstmt.setString(2, accountName);
            pstmt.executeUpdate();
            System.out.println("Actualizado el saldo de la cuenta '" + accountName + "' en " + amount);
        }
    }
}