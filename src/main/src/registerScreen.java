import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class registerScreen extends JPanel {
    private JTextField nameField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private JLabel feedbackLabel;

    private Main mainFrame;
    private Gebruiker gebruiker;

    public registerScreen(Main mainFrame) {
        this.mainFrame = mainFrame;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;


        nameField = new JTextField(20);
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        registerButton = new JButton("Registreren");
        feedbackLabel = new JLabel("");


        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Volledige naam:"), gbc);

        gbc.gridx = 1;
        add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Gebruikersnaam:"), gbc);

        gbc.gridx = 1;
        add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Wachtwoord:"), gbc);

        gbc.gridx = 1;
        add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(registerButton, gbc);

        gbc.gridy = 4;
        add(feedbackLabel, gbc);


        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());


                if (name.isBlank() || username.isBlank() || password.isBlank()) {
                    feedbackLabel.setText("Alle velden moeten ingevuld zijn.");
                    return;
                }

                gebruiker = new Gebruiker(name, username, password);


                feedbackLabel.setText("Registratie succesvol!");


                mainFrame.switchToLoginScreen();
            }
        });
    }
}
