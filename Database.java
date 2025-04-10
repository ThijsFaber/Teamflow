import java.sql.*;
import java.util.ArrayList;

public class Database {
    // inloginformatie
    String url = "jdbc:mysql://localhost:3306/scrumassistant";
    String username = "root";
    String password = "";

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



    public static Connection getConnection() throws SQLException {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/scrumassistant", "root", "XO0hbdse123!");
        }


    public void toonAllEpics() {
        Statement stmt = null;
        ArrayList <Epics> epics = new ArrayList<>();
        // connecten tot de database
        try {
            Connection con = DriverManager.getConnection(url, username, password);
            stmt = con.createStatement();
            String query = "SELECT EpicID, Titel, Beschrijving FROM epics";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int epicID = rs.getInt("EpicID");
                String titel = rs.getString("Titel");
                String beschrijving = rs.getString("Beschrijving");
                epics.add(new Epics(epicID, titel, beschrijving));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Alle epics tonen...");
        System.out.println("EpicID   Titel");
        for (Epics epic : epics) {
            System.out.print(epic.getEpicID() + "     ");
            System.out.println(epic.getTitel());
        }
    }
    public void toonAllUserStorys() {
        Statement stmt = null;
        ArrayList <UserStories> userstorys = new ArrayList<>();
        // connecten tot de database
        try {
            Connection con = DriverManager.getConnection(url, username, password);
            stmt = con.createStatement();
            String query = "SELECT UserStoryID, Titel, Beschrijving, Epic_ID FROM userstories";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int userstoryID = rs.getInt("UserStoryID");
                String titel = rs.getString("Titel");
                String beschrijving = rs.getString("Beschrijving");
                int epicID = rs.getInt("Epic_ID");
                userstorys.add(new UserStories(userstoryID, titel, beschrijving, epicID));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Alle epics tonen...");
        System.out.println("EpicID   Titel");
        for (UserStories userStories : userstorys) {
            System.out.print(userStories.getUserstoryID() + "     ");
            System.out.print(userStories.getTitel() + "      ");
            System.out.println("Deze User Story is gekoppeld aan epic: " + userStories.getEpicID());
        }
    }
    public void toonAllTaken() {
        Statement stmt = null;
        ArrayList <Taken> takens = new ArrayList<>();
        // connecten tot de database
        try {
            Connection con = DriverManager.getConnection(url, username, password);
            stmt = con.createStatement();
            String query = "SELECT ta.TaakID, ta.Titel, ta.Beschrijving, ta.UserStory_ID, us.Titel, us.Epic_ID, e.Titel  FROM taken as ta LEFT JOIN userstories as us on ta.UserStory_ID = us.UserStoryID left join epics as e on us.Epic_ID = e.EpicID";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int taakID = rs.getInt("TaakID");
                String titel = rs.getString("Titel");
                String beschrijving = rs.getString("Beschrijving");
                int userstoryID = rs.getInt("UserStory_ID");
                takens.add(new Taken(taakID, titel, beschrijving, userstoryID));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Alle epics tonen...");
        System.out.println("EpicID   Titel");
        for (Taken taak : takens) {
            System.out.print(taak.getTaakID() + "     ");
            System.out.print(taak.getTitel() + "     ");
            System.out.println("Deze taak is gekoppeld aan User Story: " + taak.getUserstoryID());
        }
    }
}
