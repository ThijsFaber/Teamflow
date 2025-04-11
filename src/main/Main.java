import javax.swing.*;
import java.awt.*;

public static void main(String[] args) {
    SwingUtilities.invokeLater(Main::new);
}

public Main() {
    setTitle("Gebruikers Systeem");
    setSize(800, 600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    cardLayout = new CardLayout();
    cardPanel = new JPanel(cardLayout);

    loginScreen loginScreen = new loginScreen(this);
    registerScreen registerScreen = new registerScreen(this);
    ThreadScreen threadScreen = new ThreadScreen(this, gebruiker);

    cardPanel.add(loginScreen, "login");
    cardPanel.add(registerScreen, "register");
    cardPanel.add(threadScreen, "thread");


    add(cardPanel);
    cardLayout.show(cardPanel, "thread");

    setVisible(true);
}