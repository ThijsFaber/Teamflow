import java.sql.*;
import java.util.ArrayList;

public class Database {
    // inloginformatie
    String url = "jdbc:mysql://localhost:3306/naamVanDatabase";
    String username = "USERNAME";
    String password = "PASSWORD";

    public String getUrl() {
        return this.url;
    }
    public String getUsername() {
        return  this.username;
    }
    public String getPassword() {
        return this.password;
    }
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

    public ArrayList getAllScrumelements() {
        Statement stmt = null;
        ArrayList <Scrumelement> scrumelements = new ArrayList<>();
        // connecten tot de database
        try {
            Connection con = DriverManager.getConnection(url, username, password);
            stmt = con.createStatement();
            DatabaseMetaData metaData = con.getMetaData();
            ResultSet rs = metaData.getTables(null, null, "scrumelement", null);
            while (rs.next()) {
                int scrumelementid = rs.getInt("idscrumelement");
                String type = rs.getString("type");
                scrumelements.add(new Scrumelement(scrumelementid, type));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return scrumelements;
    }
}
