import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseQueries {

    public static void getProyectosByDepartamento(int idDpto) throws SQLException {
        String query = "SELECT * FROM Proyecto WHERE IDDpto = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idDpto);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println("IDProy: " + rs.getInt("IDProy") + ", Nombre: " + rs.getString("Nombre"));
            }
        }
    }

    public static void getIngenierosByProyecto(int idProy) throws SQLException {
        String query = "SELECT Ingeniero.IDIng, Ingeniero.Nombre FROM Ingeniero " +
                       "JOIN Proyecto ON Ingeniero.IDIng = Proyecto.Ingeniero WHERE Proyecto.IDProy = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idProy);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println("IDIng: " + rs.getInt("IDIng") + ", Nombre: " + rs.getString("Nombre"));
            }
        }
    }
}
