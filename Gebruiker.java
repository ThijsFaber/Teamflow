import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Gebruiker {
    Scanner scanning = new Scanner(System.in);

    private int gebruikerID;
    private String naam;
    private String rol;

    public Gebruiker(int gebruikerID, String naam, String rol) throws SQLException {
        this.gebruikerID = gebruikerID;
        this.naam = naam;
        this.rol = rol;
    }
    public String getNaam() {return naam;}
    public int getGebruikerID() {return gebruikerID;}
    Database database = new Database();

    public void stuurBerichtNaarDB(String text, String scrumType, boolean isThread, String timestamp) {
        String query = "INSERT INTO bericht (Tekst, datum, AfzenderID) VALUES (?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, text);
            stmt.setString(2, timestamp);
            stmt.setInt(3, gebruikerID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String taakBericht(String text, int threadhoren, String datum) {
        database.toonAllTaken();
        System.out.println("Geef TaakID");
        int taakID = scanning.nextInt();
        System.out.println("Geef userstoryID");
        int userstoryID = scanning.nextInt();
        System.out.println("Geef epicID");
        int epicID = scanning.nextInt();
        if (threadhoren == 1) {
            System.out.println("Geef threadID");
            int threadID = scanning.nextInt();
            return "INSERT INTO `scrumassistant`.`bericht`(`Tekst`,`Datum`,`AfzenderID`,`Thread_ID`,`Epic_ID`,`UserStory_ID`,`Taak_ID`) VALUES (\""+ text + "\", \"" + datum + "\", " + this.gebruikerID + ", " + threadID + ", " + epicID + ", " + userstoryID + ", " + taakID+ ")";
        }
        return "INSERT INTO `scrumassistant`.`bericht`(`Tekst`,`Datum`,`AfzenderID`,`Epic_ID`,`UserStory_ID`,`Taak_ID`) VALUES (\"" + text + "\", \"" + datum + "\", " + this.gebruikerID + ", " + epicID + ", " + userstoryID + ", " + taakID+ ")";
    }
    private String userstoryBericht(String text, int threadhoren, String datum) {
        System.out.println("Geef userstoryID");
        int userstoryID = scanning.nextInt();
        System.out.println("Geef epicID");
        int epicID = scanning.nextInt();
        if (threadhoren == 1) {
            System.out.println("Geef threadID");
            int threadID = scanning.nextInt();
            return "INSERT INTO `scrumassistant`.`bericht`(`Tekst`,`Datum`,`AfzenderID`,`Thread_ID`,`Epic_ID`,`UserStory_ID`) VALUES (\""+ text + "\", \"" + datum + "\", " + this.gebruikerID + ", " + threadID + ", " + epicID + ", " + userstoryID +")";
        }
        return "INSERT INTO `scrumassistant`.`bericht`(`Tekst`,`Datum`,`AfzenderID`,`Epic_ID`,`UserStory_ID`) VALUES (\"" + text + "\", \"" + datum + "\", " + this.gebruikerID + ", " + epicID + ", " + userstoryID + ")";

    }
    private String epicBericht(String text, int threadhoren, String datum) {
        database.toonAllEpics();

        System.out.println("Geef epicID");
        int epicID = scanning.nextInt();
        if (threadhoren == 1) {
            System.out.println("Geef threadID");
            int threadID = scanning.nextInt();
            return "INSERT INTO `scrumassistant`.`bericht`(`Tekst`,`Datum`,`AfzenderID`,`Thread_ID`,`Epic_ID`) VALUES (\""+ text + "\", \"" + datum + "\", " + this.gebruikerID + ", " + threadID + ", " + epicID + ")";

        }
        return "INSERT INTO `scrumassistant`.`bericht`(`Tekst`,`Datum`,`AfzenderID`,`Epic_ID`) VALUES (\"" + text + "\", \"" + datum + "\", " + this.gebruikerID + ", " + epicID + ")";

    }


    public void toonBerichten(String SQL, ArrayList<Gebruiker> gebruikers) {
        try {
            String url = database.getUrl();
            String username = database.getUsername();
            String password = database.getPassword();
            Connection con = DriverManager.getConnection(url, username, password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);
            while(rs.next()) {
                String verstuurder = null;
                String datum = rs.getString("datum");
                String textBericht = rs.getString("Tekst");
                String epicTitel = rs.getString("epicTitel");
                String userstoryTitel = rs.getString("userstoryTitel");
                String taakTitel = rs.getString("taakTitel");
                String threadTitel = rs.getString("threadTitel");
                for (Gebruiker gebruikercheck : gebruikers) {
                    if (gebruikercheck.getGebruikerID() == rs.getInt("afzenderID")) {
                        verstuurder = gebruikercheck.getNaam();
                        break;
                    }
                }
                if (!(threadTitel == null)) {
                    System.out.println("Dit bericht behoord tot thread: " + threadTitel + ". En is verzonden op " + datum);
                } else if (!(taakTitel == null)) {
                    System.out.println("Dit bericht behoord tot taak: " + taakTitel + ". En is verzonden op " + datum);
                } else if (!(userstoryTitel == null)) {
                    System.out.println("Dit bericht behoord tot user story: " + userstoryTitel + ". En is verzonden op " + datum);
                } else if (!(epicTitel == null)) {
                    System.out.println("Dit bericht behoord tot epic: " + epicTitel + ". En is verzonden op " + datum);
                } else {
                    System.out.println("Dit bericht behoord tot geen scrumelement en is verzonden op " + datum);
                }
                System.out.print(verstuurder + ": ");
                System.out.println(textBericht + "\n");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public String getRol() {
        return rol;
    }
}
