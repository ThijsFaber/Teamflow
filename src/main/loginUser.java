import java.sql.*;

public class loginUser {
    public static String loginUser(int id, String naam, Connection conn) throws SQLException {
        String sql = "SELECT Rol FROM Gebruiker WHERE GebruikerID = ? AND LOWER(Naam) = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.setString(2, naam.toLowerCase());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("Rol");
            } else {
                return null;
            }
        }
    }
}
