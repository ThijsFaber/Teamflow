public class Bericht {
    private int berichtID;
    String text;
    int afzenderID;
    String datum;
    private Scrumelement koppeldAan;
    public Bericht(String text, int afzenderID, String datum, Scrumelement koppeldAan){
        this.text = text;
        this.afzenderID = afzenderID;
        this.datum = datum;
    }
    public int getScrumelemntID() {
        return koppeldAan.getIdscrumelement();
    }
}
