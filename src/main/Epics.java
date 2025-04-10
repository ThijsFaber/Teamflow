public class Epics {
    protected int epicID;
    protected String epicTitel;
    protected String epicBeschrijving;

    public Epics (int epicID, String titel, String beschrijving) {
        this.epicID = epicID;
        this.epicTitel = titel;
        this.epicBeschrijving = beschrijving;
    }


    public int getEpicID() {
        return  epicID;
    }
    public String getEpicTitel() {
        return epicTitel;
    }
}
