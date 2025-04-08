public class Taken {
    private int taakID;
    private String titel;
    private String beschrijving;
    private int userstoryID;

    public Taken (int taakID, String titel, String beschrijving, int userstoryID) {
        this.taakID = taakID;
        this.titel = titel;
        this.beschrijving = beschrijving;
        this.userstoryID = userstoryID;
    }
    public int getUserstoryID() {
        return userstoryID;
    }
    public int getTaakID() {
        return taakID;
    }
    public String getTitel() {
        return titel;
    }
    public String getBeschrijving() {
        return beschrijving;
    }
}
