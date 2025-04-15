import java.sql.*;
import java.util.ArrayList;

public class Database {

    String url = "jdbc:mysql://localhost:3306/scrumassistant";
    String username = "root";
    String password = "Tyson1990!";

    public String getUrl() {
        return this.url;
    }
    public String getUsername() {
        return  this.username;
    }
    public String getPassword() {
        return this.password;
    }
    public void executeQuery(String Query) throws  SQLException {
        Statement stmt = null;


        try {
            Connection con = DriverManager.getConnection(url, username, password);
            stmt = con.createStatement();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        stmt.execute(Query);
    }

    public ArrayList<Epics> toonAllEpics() {
        Statement stmt;
        ArrayList <Epics> epics = new ArrayList<>();


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
        for (Epics epic : epics) {
            System.out.print("EpicID: " + epic.getEpicID() + ". ");
            System.out.println("Met Titel: " + epic.getEpicTitel() + ".\n");
        }
        return epics;
    }
    public ArrayList<UserStories> toonAllUserStorys() {
        Statement stmt;
        ArrayList <UserStories> userstorys = new ArrayList<>();


        try {
            Connection con = DriverManager.getConnection(url, username, password);
            stmt = con.createStatement();
            String query = "SELECT us.UserStoryID, us.Titel as userstoryTitel, us.Beschrijving as userstoryBeschrijving, us.Epic_ID, e.Titel as epicTitel, e.Beschrijving as epicBeschrijving  FROM userstories as us LEFT JOIN epics as e on us.Epic_ID = e.EpicID";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int userstoryID = rs.getInt("UserStoryID");
                String titel = rs.getString("userstoryTitel");
                String beschrijving = rs.getString("userstoryBeschrijving");
                int epicID = rs.getInt("Epic_ID");
                String epicTitel = rs.getString("epicTitel");
                String epicBeschrijving = rs.getString("epicBeschrijving");
                userstorys.add(new UserStories(userstoryID, titel, beschrijving, epicID, epicTitel, epicBeschrijving));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Alle User Story's tonen...");
        for (UserStories userStories : userstorys) {
            System.out.print("User StoryID: " + userStories.getUserstoryID() + ". ");
            System.out.println("Met Titel: " + userStories.getUserstoryTitel() + ". ");
            System.out.print("    Dit is gekoppeld aan EpicID: " + userStories.getEpicID() + ". ");
            System.out.println("Met Titel: " + userStories.getEpicTitel() + ".\n");
        }
        return userstorys;
    }
    public ArrayList<Taken> toonAllTaken() {
        Statement stmt;
        ArrayList <Taken> takens = new ArrayList<>();


        try {
            Connection con = DriverManager.getConnection(url, username, password);
            stmt = con.createStatement();
            String query = "SELECT ta.TaakID, ta.Titel, ta.Beschrijving, ta.UserStory_ID, us.Titel as usTitel, us.Beschrijving as usBeschrijving, us.Epic_ID, e.Titel as eTitel, e.Beschrijving as eBeschrijving FROM taken as ta LEFT JOIN userstories as us on ta.UserStory_ID = us.UserStoryID left join epics as e on us.Epic_ID = e.EpicID";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int taakID = rs.getInt("TaakID");
                String titel = rs.getString("Titel");
                String beschrijving = rs.getString("Beschrijving");
                int userstoryID = rs.getInt("UserStory_ID");
                String userstoryTitel = rs.getString("usTitel");
                String userstoryBeschrijving = rs.getString("usBeschrijving");
                int epicID = rs.getInt("Epic_ID");
                String epicTitel = rs.getString("eTitel");
                String epicBeschrijving = rs.getString("eBeschrijving");
                takens.add(new Taken(taakID, titel, beschrijving, userstoryID, userstoryTitel, userstoryBeschrijving, epicID, epicTitel, epicBeschrijving));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Alle taken tonen...");
        for (Taken taak : takens) {
            System.out.print("TaakID: " + taak.getTaakID() + ". ");
            System.out.println("taak Titel: " + taak.getTaakTitel() + ". ");
            System.out.print("    Deze taak is gekoppeld aan User StoryID: " + taak.getUserstoryID() + ". ");
            System.out.println("Met Titel: " + taak.getUserstoryTitel() + ". ");
            System.out.print("        Dit is verder gekoppeld aan EpicID: " + taak.getEpicID() + ". ");
            System.out.println("Met Titel: " + taak.getEpicTitel() + ".\n");
        }
        return takens;
    }
}
