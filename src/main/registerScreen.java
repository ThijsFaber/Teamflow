import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class registerScreen extends JPanel {
    public registerScreen(Main mainFrame) {
        setLayout(new GridLayout(3, 2));

        JTextField nameField = new JTextField();
        JTextField roleField = new JTextField();
        JButton registerButton = new JButton("Registreren");

        add(new JLabel("Naam:"));
        add(nameField);
        add(new JLabel("Rol:"));
        add(roleField);
        add(new JLabel());
        add(registerButton);

        registerButton.addActionListener(e -> {
            try (Connection conn = Account.connect()) {
                int userId = registerUser.registerUser(nameField.getText(), roleField.getText(), conn);
                JOptionPane.showMessageDialog(mainFrame,
                        "Registratie succesvol!\nJe gebruikers-ID is: " + userId + "\nGebruik dit ID om in te loggen.");
                mainFrame.switchTo("login");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(mainFrame, "Fout bij registratie.");
            }
        });
}}
