import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        Gebruiker gebruiker = new Gebruiker(1, "Alexander", "Sprint manager");
        //gebruiker.stuurBericht();
        try {
            String url = "jdbc:mysql://localhost:3306/naamVanDeDatabase";
            String username = "USERNAME";
            String password = "PASSWORD";
            Connection con = DriverManager.getConnection(url, username, password);
            Statement stmt = con.createStatement();
            String SQL = ("SELECT * FROM users.berichten");
            ResultSet rs = stmt.executeQuery(SQL);
            while(rs.next()) {
                System.out.println("Dit bericht is verzonden door: " + rs.getString("afzenderID"));
                System.out.println(rs.getString("text"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

