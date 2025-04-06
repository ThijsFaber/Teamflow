import java.time.LocalDateTime;

public class Bericht {
    private int berichtID;
    String text;
    int afzenderID;
    LocalDateTime timeSent;
    public Bericht(String text, int afzenderID, LocalDateTime timeSent){
        this.text = text;
        this.afzenderID = afzenderID;
        this.timeSent = timeSent;
    }
}
