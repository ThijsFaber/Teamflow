import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
public class Main extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    public static void main(String[] args) throws SQLException {
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
        
        setVisible(true);
    }
    public void switchTo(String screenName) {
        cardLayout.show(cardPanel, screenName);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}