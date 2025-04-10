public class Taken extends UserStories{
    protected int taakID;
    protected String taakTitel;
    protected String taakBeschrijving;

    public Taken (int taakID, String taakTitel, String taakBeschrijving, int userstoryID, String userstorytitel, String userstoryBeschrijving, int epicID, String epicTitel, String epicBeschrijving) {
        super(userstoryID, userstorytitel, userstoryBeschrijving, epicID, epicTitel, epicBeschrijving);
        this.taakID = taakID;
        this.taakTitel = taakTitel;
        this.taakBeschrijving = taakBeschrijving;
    }

    public int getTaakID() {
        return taakID;
    }
    public String getTaakTitel() {
        return taakTitel;
    }
    public String getTaakBeschrijving() {
        return taakBeschrijving;
    }
}
