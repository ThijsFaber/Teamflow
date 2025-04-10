public class Epics {
    private int epicID;
    private String titel;
    private String beschrijving;

    public Epics (int epicID, String titel, String beschrijving) {
        this.epicID = epicID;
        this.titel = titel;
        this.beschrijving = beschrijving;
    }
    public int getEpicID() {
        return  epicID;
    }
    public String getTitel() {
        return titel;
    }
}
