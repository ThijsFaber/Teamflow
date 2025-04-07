import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Gebruiker gebruiker = new Gebruiker(1, "Alexander", "Sprint manager");
        Scrumelement epic1 = new Scrumelement(1, "Epic");
        ArrayList<Gebruiker> gebruikers = new ArrayList<>();
        ArrayList<Scrumelement> scrumelementen = new ArrayList<>();
        scrumelementen.add(epic1);
        gebruikers.add(gebruiker);
        System.out.println("Toets 1 om een bericht te sturen, toets 2 om alle berichten te tonen");
        Scanner sc = new Scanner(System.in);
        int keuze = sc.nextInt();
        sc.nextLine();
        if (keuze == 1) {
            gebruiker.stuurBericht();
        } else if (keuze == 2) {
            String SQL = "SELECT * FROM scrumassistant.bericht LEFT JOIN scrumassistant.thread ON thread.ThreadID = bericht.Thread_ID LEFT JOIN scrumassistant.taken ON taken.TaakID = bericht.Taak_ID LEFT JOIN scrumassistant.epics ON epics.EpicID = bericht.Epic_ID LEFT JOIN scrumassistant.userstories ON userstories.UserStoryID = bericht.UserStory_ID ORDER BY bericht.datum desc";
            gebruiker.toonBerichten(SQL, gebruikers);
        }
    }
}