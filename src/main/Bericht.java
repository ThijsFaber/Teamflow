public class Bericht {
    private int berichtID;
    String text;
    int afzenderID;
    String datum;
    private int threadID;
    private int epicID;
    private int userstoryID;
    private int taakID;

    public Bericht(String text, int afzenderID, String datum, Scrumelement koppeldAan){
        this.text = text;
        this.afzenderID = afzenderID;
        this.datum = datum;
    }

}
