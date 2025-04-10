import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private Gebruiker gebruiker;

    public Main() {
        setTitle("Gebruikers Systeem");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        loginScreen loginScreen = new loginScreen(this);
        registerScreen registerScreen = new registerScreen(this);


        cardPanel.add(loginScreen, "login");
        cardPanel.add(registerScreen, "register");

        add(cardPanel);
        cardLayout.show(cardPanel, "login");

        setVisible(true);
    }

    public void loginSuccess(Gebruiker gebruiker) {
        MessageScreen messageScreen = new MessageScreen(gebruiker);
        cardPanel.add(messageScreen, "message");
        switchTo("message");
    }


    public void switchTo(String screenName) {
        cardLayout.show(cardPanel, screenName);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}