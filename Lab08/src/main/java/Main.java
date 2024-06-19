import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            // Insertar un Departamento
            DatabaseOperations.insertDepartamento(3, "Departamento C", "123456789", "987654321");
            System.out.println("Departamento insertado exitosamente.");

            // Consultar Proyectos de un Departamento
            DatabaseQueries.getProyectosByDepartamento(3);

            // Consultar Ingenieros en un Proyecto
            DatabaseQueries.getIngenierosByProyecto(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
