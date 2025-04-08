import java.sql.*;
import java.util.Scanner;

public class registerUser {
    public static int registerUser(String naam, String rol, Connection conn) throws SQLException {
        String sql = "INSERT INTO Gebruiker (Naam, Rol) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, naam);
            stmt.setString(2, rol);
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Geen ID gegenereerd.");
            }
        }
    }
}

