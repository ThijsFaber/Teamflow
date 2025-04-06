import java.sql.*;

public class Database {
    // inloginformatie
    String url = "jdbc:mysql://localhost:3306/naamVanDeDatabase";
    String username = "USERNAME";
    String password = "PASSWORD";

    public void executeQuery(String Query) throws  SQLException { // methode om een query zonder resultaat uit te voeren
        Statement stmt = null;
        // connecten tot de database
        try {
            Connection con = DriverManager.getConnection(url, username, password);
            stmt = con.createStatement();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        // de query uitvoeren
        stmt.execute(Query);
    }
}