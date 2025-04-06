import java.sql.SQLException;

import java.util.Scanner;
import java.time.LocalDateTime;

public class Gebruiker {
    Scanner scanning = new Scanner(System.in);
    LocalDateTime currentDateTime = LocalDateTime.now();

    private int gebruikerID;
    private String naam;
    private String rol;
    public Gebruiker(int gebruikerID, String naam, String rol) throws SQLException {
        this.gebruikerID = gebruikerID;
        this.naam = naam;
        this.rol = rol;
    }
    public void stuurBericht() throws SQLException {
        String text = scanning.nextLine();
        Bericht bericht = new Bericht(text, this.gebruikerID, currentDateTime);
        Database database = new Database();
        String Query = "INSERT INTO `users`.`berichten` (`text`,`afzenderID`) VALUES(\"" + text + "\", \"" + this.gebruikerID + "\")";
        database.executeQuery(Query);

    }
}

