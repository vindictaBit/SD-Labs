import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseOperations {

    public static void insertDepartamento(int idDpto, String nombre, String telefono, String fax) throws SQLException {
        String query = "INSERT INTO Departamento (IDDpto, Nombre, Telefono, Fax) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idDpto);
            stmt.setString(2, nombre);
            stmt.setString(3, telefono);
            stmt.setString(4, fax);
            stmt.executeUpdate();
        }
    }

    public static void updateDepartamento(int idDpto, String nombre, String telefono, String fax) throws SQLException {
        String query = "UPDATE Departamento SET Nombre = ?, Telefono = ?, Fax = ? WHERE IDDpto = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nombre);
            stmt.setString(2, telefono);
            stmt.setString(3, fax);
            stmt.setInt(4, idDpto);
            stmt.executeUpdate();
        }
    }

    public static void deleteDepartamento(int idDpto) throws SQLException {
        String query = "DELETE FROM Departamento WHERE IDDpto = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idDpto);
            stmt.executeUpdate();
        }
    }
}
