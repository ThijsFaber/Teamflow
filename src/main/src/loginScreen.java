import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class loginScreen extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel feedbackLabel;

    private Main mainFrame;
    private Gebruiker gebruiker;

    public loginScreen(Main mainFrame, Gebruiker gebruiker) {
        this.mainFrame = mainFrame;
        this.gebruiker = gebruiker;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;


        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Inloggen");
        feedbackLabel = new JLabel("");


        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Gebruikersnaam:"), gbc);

        gbc.gridx = 1;
        add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Wachtwoord:"), gbc);

        gbc.gridx = 1;
        add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(loginButton, gbc);

        gbc.gridy = 3;
        add(feedbackLabel, gbc);


        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());


                if (username.equals("admin") && password.equals("password123")) {
                    feedbackLabel.setText("Inloggen succesvol!");

                    mainFrame.switchToThreadScreen();
                    feedbackLabel.setText("Foutieve gebruikersnaam of wachtwoord.");
                }
            }
        });
    }
}
