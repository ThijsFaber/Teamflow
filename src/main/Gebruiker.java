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

    public Gebruiker(int gebruikerID, String naam, String rol) {
        this.gebruikerID = gebruikerID;
        this.naam = naam;
        this.rol = rol;
    }
    public String getNaam() {return naam;}
    public int getGebruikerID() {return gebruikerID;}
    public String getRol(){return rol;}
    Database database = new Database();


    private String taakBericht(String text, int threadhoren, String datum) {
        ArrayList<Taken> takens = database.toonAllTaken();
        System.out.println("Geef TaakID");
        int taakID = scanning.nextInt();
        int userstoryID = 0;
        int epicID = 0;
        for (Taken taak : takens) {
            if (taak.getTaakID() == taakID) {
                userstoryID = taak.getUserstoryID();
                epicID = taak.getEpicID();
            }
        }
        if (threadhoren == 1) {
            System.out.println("Geef threadID");
            int threadID = scanning.nextInt();
            return "INSERT INTO `scrumassistant`.`bericht`(`Tekst`,`Datum`,`AfzenderID`,`Thread_ID`,`Epic_ID`,`UserStory_ID`,`Taak_ID`) VALUES (\""+ text + "\", \"" + datum + "\", " + this.gebruikerID + ", " + threadID + ", " + epicID + ", " + userstoryID + ", " + taakID+ ")";
        }
        return "INSERT INTO `scrumassistant`.`bericht`(`Tekst`,`Datum`,`AfzenderID`,`Epic_ID`,`UserStory_ID`,`Taak_ID`) VALUES (\"" + text + "\", \"" + datum + "\", " + this.gebruikerID + ", " + epicID + ", " + userstoryID + ", " + taakID+ ")";
    }
    private String userstoryBericht(String text, int threadhoren, String datum) {
        ArrayList<UserStories> userStories = database.toonAllUserStorys();
        System.out.println("Geef userstoryID");
        int userstoryID = scanning.nextInt();
        int epicID = 0;
        for (UserStories userStory : userStories) {
            if (userStory.getUserstoryID() == userstoryID) {
                epicID = userStory.getEpicID();
            }
        }
        if (threadhoren == 1) {
            System.out.println("Geef threadID");
            int threadID = scanning.nextInt();
            return "INSERT INTO `scrumassistant`.`bericht`(`Tekst`,`Datum`,`AfzenderID`,`Thread_ID`,`Epic_ID`,`UserStory_ID`) VALUES (\""+ text + "\", \"" + datum + "\", " + this.gebruikerID + ", " + threadID + ", " + epicID + ", " + userstoryID +")";
        }
        return "INSERT INTO `scrumassistant`.`bericht`(`Tekst`,`Datum`,`AfzenderID`,`Epic_ID`,`UserStory_ID`) VALUES (\"" + text + "\", \"" + datum + "\", " + this.gebruikerID + ", " + epicID + ", " + userstoryID + ")";

    }
    private String epicBericht(String text, int threadhoren, String datum) {
        ArrayList<Epics> epics = database.toonAllEpics();

        System.out.println("Geef epicID");
        int epicID = scanning.nextInt();
        if (threadhoren == 1) {
            System.out.println("Geef threadID");
            int threadID = scanning.nextInt();
            return "INSERT INTO `scrumassistant`.`bericht`(`Tekst`,`Datum`,`AfzenderID`,`Thread_ID`,`Epic_ID`) VALUES (\""+ text + "\", \"" + datum + "\", " + this.gebruikerID + ", " + threadID + ", " + epicID + ")";

        }
        return "INSERT INTO `scrumassistant`.`bericht`(`Tekst`,`Datum`,`AfzenderID`,`Epic_ID`) VALUES (\"" + text + "\", \"" + datum + "\", " + this.gebruikerID + ", " + epicID + ")";

    }

    public void stuurBericht() throws SQLException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime currentDateTime = LocalDateTime.now();
        String formatted = currentDateTime.format(formatter);


        System.out.println("Behoort dit tot een \"Epic\", \"User Story\" of \"Taak\"?");
        String scrumelement = scanning.nextLine();
        if (!(scrumelement.equals("Epic") || scrumelement.equals("User Story") || scrumelement.equals("Taak"))) {
            System.out.println("Er lijkt een typefout te zijn, probeer het opnieuw");
            this.stuurBericht();
        } else {
            System.out.println("Behoort dit bericht tot een thread \n 1. Ja \n 2. Nee");
            int threadBehoren = scanning.nextInt();
            scanning.nextLine();
            System.out.println("Typ uw bericht hier:");
            String text = scanning.nextLine();
            String query = switch (scrumelement) {
                case "Taak" -> this.taakBericht(text, threadBehoren, formatted);
                case "User Story" -> this.userstoryBericht(text, threadBehoren, formatted);
                case "Epic" -> this.epicBericht(text, threadBehoren, formatted);
                default -> null;
            };
            database.executeQuery(query);
        }
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

    public void sprintMaken(){
        System.out.println("Tot wanneer moet deze sprint duren? (met format yyyy-MM-dd uu:mm:ss)");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String eindDatum = scanning.nextLine();
        try {
            LocalDateTime eindigenDatum = LocalDateTime.parse(eindDatum, formatter);
            String SQL = "INSERT INTO `scrumassistant`.`sprint`(`Datum`)VALUES (\""+ eindigenDatum +"\");";
            database.executeQuery(SQL);
            System.out.println("Sprint succesvol aangemaakt met eind datum " + eindigenDatum);
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Ongeldig formaat. Gebruik: yyyy-MM-dd HH:mm");
        }
    }
}
