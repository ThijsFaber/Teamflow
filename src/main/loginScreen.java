import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class loginScreen extends JPanel {
    public loginScreen(Main mainFrame) {
        setLayout(new GridLayout(4, 2));

        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JButton loginButton = new JButton("Login");
        JButton goToRegister = new JButton("Registreren");

        add(new JLabel("GebruikersID:"));
        add(idField);
        add(new JLabel("Naam:"));
        add(nameField);
        add(loginButton);
        add(goToRegister);

        loginButton.addActionListener(e -> {
            try (Connection conn = Account.connect()) {
                int id = Integer.parseInt(idField.getText());
                String naam = nameField.getText();
                String rol = loginUser.loginUser(id, naam, conn);

                if (rol != null) {
                    JOptionPane.showMessageDialog(mainFrame, "Ingelogd als: " + rol);
                    mainFrame.switchTo("welcome");
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "Inloggegevens onjuist.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(mainFrame, "Fout bij inloggen.");
            }
        });

        goToRegister.addActionListener(e -> mainFrame.switchTo("register"));
    }
}
