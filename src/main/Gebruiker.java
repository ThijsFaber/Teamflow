import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Gebruiker {
    Scanner scanning = new Scanner(System.in);

    private int gebruikerID;
    private String naam;
    private String rol;

    public Gebruiker(int gebruikerID, String naam, String rol) throws SQLException {
        this.gebruikerID = gebruikerID;
        this.naam = naam;
        this.rol = rol;
    }
    public String getNaam() {return naam;}
    public int getGebruikerID() {return gebruikerID;}
    Database database = new Database();
    public void stuurBericht() throws SQLException {
        //formatten zodat connectie code databse goed gaat
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime currentDateTime = LocalDateTime.now();
        String formatted = currentDateTime.format(formatter);

        System.out.println("Kies uw scrumelement:");
        int koppelingsID = scanning.nextInt();
        scanning.nextLine();
        System.out.println("Typ uw bericht hier:");
        String text = scanning.nextLine();
        Scrumelement gekoppeld = null;
        Database database = new Database();
        ArrayList<Scrumelement> scrumelementlijst = database.getAllScrumelements();
        for (Scrumelement scrumelementCheck : scrumelementlijst) {
            if (scrumelementCheck.getIdscrumelement() == koppelingsID) {
                gekoppeld = scrumelementCheck;
                break;
            }
        }

        Bericht bericht = new Bericht(text, this.gebruikerID, formatted, gekoppeld);
        String Query = "INSERT INTO `users`.`berichten` (`text`,`afzenderID`, `datum`, `idscrumelement`) VALUES(\"" + text + "\", \"" + this.gebruikerID + "\", \"" + formatted + "\", \"" + koppelingsID + "\")";
        database.executeQuery(Query);

    }
    public void toonBerichten(String SQL, ArrayList<Gebruiker> gebruikers) {
        try {
            String url = database.getUrl();
            String username = database.getUsername();
            String password = database.getPassword();
            Connection con = DriverManager.getConnection(url, username, password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);
            while(rs.next()) {
                String verstuurder = null;
                String datum = rs.getString("datum");
                String textBericht = rs.getString("text");
                String typeElement = rs.getString("type");
                for (Gebruiker gebruikercheck : gebruikers) {
                    if (gebruikercheck.getGebruikerID() == rs.getInt("afzenderID")) {
                        verstuurder = gebruikercheck.getNaam();
                        break;
                    }
                }
                System.out.println("Dit bericht behoord tot scrumelement: " + typeElement + " en is verzonden op " + datum);
                System.out.print(verstuurder + ": ");
                System.out.println(textBericht);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
