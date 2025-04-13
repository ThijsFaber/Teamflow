import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Authenticator {

    public void register(String naam, String rol) throws SQLException {
        String query = "INSERT INTO Gebruiker (Naam, Rol) VALUES (?, ?)";
        try (Connection conn = Account.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, naam);
            stmt.setString(2, rol);
            stmt.executeUpdate();
        }
    }

    public Gebruiker login(String naam, int gebruikerID) throws SQLException {
        String query = "SELECT * FROM Gebruiker WHERE Naam = ? AND GebruikerID = ?";
        try (Connection conn = Account.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, naam);
            stmt.setInt(2, gebruikerID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Gebruiker(
                        rs.getInt("GebruikerID"),
                        rs.getString("Naam"),
                        rs.getString("Rol")
                );
            }
        }
        return null;
    }
}
