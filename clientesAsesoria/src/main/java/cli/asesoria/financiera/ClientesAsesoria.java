
package cli.asesoria.financiera;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientesAsesoria {

    private static final String URL = "Acá ira la dirección de base de datos";
    private static final String USUARIO = "Acá irá el usuario en particular";
    private static final String CONTRASEÑA = "";

    private Connection conexion;

    public ClientesAsesoria() {
        try {
            conexion = DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insercionInfo(String nombre, int edad) {
        String consulta = "INSERT INTO personas (nombre, edad) VALUES (?, ?)";
        try {
            PreparedStatement statement = conexion.prepareStatement(consulta);
            statement.setString(1, nombre);
            statement.setInt(2, edad);
            statement.executeUpdate();
            System.out.println("La información se ha insertado de forma adecuada.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void consultaInfo() {
        String consulta = "SELECT * FROM personas";
        try {
            PreparedStatement statement = conexion.prepareStatement(consulta);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String nombre = resultSet.getString("nombre");
                int edad = resultSet.getInt("edad");
                System.out.println("Nombre: " + nombre + ", Edad: " + edad);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizacionInfo(String nombreAnterior, String nuevoNombre) {
        String consulta = "UPDATE personas SET nombre = ? WHERE nombre = ?";
        try {
            PreparedStatement statement = conexion.prepareStatement(consulta);
            statement.setString(1, nuevoNombre);
            statement.setString(2, nombreAnterior);
            int filasActualizadas = statement.executeUpdate();
            if (filasActualizadas > 0) {
                System.out.println("Información actualizada correctamente.");
            } else {
                System.out.println("No se encontró información para actualizar.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminacionInfo(String nombre) {
        String consulta = "DELETE FROM personas WHERE nombre = ?";
        try {
            PreparedStatement statement = conexion.prepareStatement(consulta);
            statement.setString(1, nombre);
            int filasEliminadas = statement.executeUpdate();
            if (filasEliminadas > 0) {
                System.out.println("La información se ha eliminado adecuadamente");
            } else {
                System.out.println("No hay información para eliminar.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ClientesAsesoria crud = new ClientesAsesoria();

        // Ejemplo de uso
        crud.insercionInfo("Juan", 25);
        crud.insercionInfo("María", 30);
        crud.consultaInfo();
        crud.actualizacionInfo("Juan", "Pedro");
        crud.eliminacionInfo("María");
        crud.consultaInfo();

        crud.cerrarConexion();
    }
}
