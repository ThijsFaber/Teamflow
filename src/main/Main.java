import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
public class Main extends JFrame {

    public static void main(String[] args) throws SQLException {

        ArrayList<Gebruiker> gebruikers = new ArrayList<>();
        Gebruiker alex = new Gebruiker(1, "Alexander", "Sprint manager");
        Gebruiker thijs = new Gebruiker(2, "Thijs", "Scrum master");
        gebruikers.add(alex);
        gebruikers.add(thijs);
        Scanner sc = new Scanner(System.in);
        System.out.println("Geef uw gebruikersID:");
        int userid = sc.nextInt();
        sc.nextLine();
        Gebruiker gebruiker = null;
        for(Gebruiker user : gebruikers){
            if (user.getGebruikerID() == userid){
                gebruiker = user;
            }
        }
        while(true) {
            System.out.print("Toets 1 om een bericht te sturen, toets 2 om alle berichten te tonen");
            if (gebruiker.getRol().equals("Scrum master")) {
                System.out.print(", toets 3 om een nieuwe sprint aan te maken");
            }

            System.out.println();
            int keuze = sc.nextInt();
            sc.nextLine();
            if (keuze == 1) {
                gebruiker.stuurBericht();
            } else if (keuze == 2) {
                String SQL = "SELECT b.BerichtID, b.tekst, b.Datum, b.AfzenderID, th.Titel as threadTitel, ta.Titel as taakTitel, us.Titel as userstoryTitel, e.Titel as epicTitel FROM scrumassistant.bericht as b LEFT JOIN scrumassistant.thread as th ON th.ThreadID = b.Thread_ID LEFT JOIN scrumassistant.taken as ta ON ta.TaakID = b.Taak_ID LEFT JOIN scrumassistant.epics as e ON e.EpicID = b.Epic_ID LEFT JOIN scrumassistant.userstories as us ON us.UserStoryID = b.UserStory_ID ORDER BY b.datum desc";
                gebruiker.toonBerichten(SQL, gebruikers);
            } else if (keuze == 3) {
                if (!gebruiker.getRol().equals("Scrum master")){
                    System.out.println("You dirty dity dog, no access for you");
                } else {
                    gebruiker.sprintMaken();
                }

            } else {
                System.out.println("Slecht antwoord probeer het nog een keer");
            }

            System.out.println("Wilt u nog meer doen? Y/N");
            String doorgaan = sc.nextLine();
            if (doorgaan.equals("N")) {
                break;
            }

        }
    }
}

/*
    private CardLayout cardLayout;
    private JPanel cardPanel;
    setTitle("Gebruikers Systeem");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Create screen panels
        loginScreen loginScreen = new loginScreen(this);
        registerScreen registerScreen = new registerScreen(this);

        // Add them to the layout
        cardPanel.add(loginScreen, "login");
        cardPanel.add(registerScreen, "register");

        add(cardPanel);
        cardLayout.show(cardPanel, "login");

        setVisible(true);
        SwingUtilities.invokeLater(Main::new);
    }
    public void switchTo(String screenName) {
        cardLayout.show(cardPanel, screenName);
    }
*/