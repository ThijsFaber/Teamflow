import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ThreadScreen extends JPanel {
    private JTextField titelField;
    private JTextField vraagField;
    private JButton aanmaakKnop;
    private JLabel feedbackLabel;

    private ThreadController controller = new ThreadController();
    private Gebruiker gebruiker;

    public ThreadScreen(Main mainFrame, Gebruiker gebruiker) {
        this.gebruiker = gebruiker;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        titelField = new JTextField(20);
        vraagField = new JTextField(20);
        aanmaakKnop = new JButton("Maak thread aan");
        feedbackLabel = new JLabel("");

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Titel van thread:"), gbc);

        gbc.gridx = 1;
        add(titelField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Hoofdvraag:"), gbc);

        gbc.gridx = 1;
        add(vraagField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(aanmaakKnop, gbc);

        gbc.gridy = 3;
        add(feedbackLabel, gbc);

        aanmaakKnop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String titel = titelField.getText();
                String vraag = vraagField.getText();
                String gebruikersNaam = gebruiker != null ? gebruiker.getNaam() : "onbekend";

                String resultaat = controller.startNieuweThread(titel, vraag, gebruikersNaam);
                feedbackLabel.setText(resultaat);
            }
        });
    }
}
