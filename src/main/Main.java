import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public void switchToThreadScreen() {
        cardLayout.show(cardPanel, "thread");
    }
    // Constructor zonder parameters, maakt het hoofdvenster aan
    public Main() {
        // Initialiseer het hoofdvenster
        setTitle("Gebruikers Systeem");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        Gebruiker gebruiker = new Gebruiker();

        // Instantie van de juiste panelen
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Maak je schermen aan
        loginScreen loginScreen = new loginScreen(this);
        registerScreen registerScreen = new registerScreen(this);
        ThreadScreen threadScreen = new ThreadScreen(this, gebruiker); // Pass de gebruiker hier door

        // Voeg je schermen toe aan het cardLayout paneel
        cardPanel.add(loginScreen, "login");
        cardPanel.add(registerScreen, "register");
        cardPanel.add(threadScreen, "thread");

        // Voeg het cardPanel toe aan het hoofdvenster
        add(cardPanel);

        // Toon het 'thread' scherm
        cardLayout.show(cardPanel, "thread");

        // Zorg ervoor dat het venster zichtbaar is
        setVisible(true);
    }

    // Main methode, start het programma
    public static void main(String[] args) {
        // Zorg ervoor dat het in de Event Dispatch Thread gebeurt
        SwingUtilities.invokeLater(() -> new Main());
    }
}
