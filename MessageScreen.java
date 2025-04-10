import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MessageScreen extends JPanel {
    private JTextArea messageDisplay;
    private JTextField inputField;
    private JButton sendButton;
    private JComboBox<String> scrumTypeCombo;
    private JCheckBox threadCheck;
    private Gebruiker gebruiker;

    public MessageScreen(Gebruiker gebruiker) {
        this.gebruiker = gebruiker;
        setLayout(new BorderLayout());

        messageDisplay = new JTextArea();
        messageDisplay.setEditable(false);
        add(new JScrollPane(messageDisplay), BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputField = new JTextField();
        sendButton = new JButton("Verzend");

        JPanel optionsPanel = new JPanel();
        scrumTypeCombo = new JComboBox<>(new String[]{"Geen", "Epic", "User Story", "Taak"});
        threadCheck = new JCheckBox("Behoort tot thread");
        optionsPanel.add(new JLabel("Type:"));
        optionsPanel.add(scrumTypeCombo);
        optionsPanel.add(threadCheck);

        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        add(inputPanel, BorderLayout.SOUTH);
        add(optionsPanel, BorderLayout.NORTH);

        sendButton.addActionListener((ActionEvent e) -> {
            String tekst = inputField.getText().trim();
            if (!tekst.isEmpty()) {
                String naam = gebruiker.getNaam();
                String rol = gebruiker.getRol();
                String scrumType = (String) scrumTypeCombo.getSelectedItem();
                boolean isThread = threadCheck.isSelected();
                String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                String bericht = naam + " (" + rol + ") [" + scrumType + "]" + (isThread ? " [Thread]" : "") + ": " + tekst;

                messageDisplay.append(bericht + "\n");
                inputField.setText("");

                gebruiker.stuurBerichtNaarDB(tekst, scrumType, isThread, timestamp);
            }
        });
    }
}