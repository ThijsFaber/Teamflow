public class UserStories {
    private int userstoryID;
    private String titel;
    private String beschrijving;
    private int epicID;

    public UserStories(int userstoryID, String titel, String beschrijving, int epicID) {
        this.userstoryID = userstoryID;
        this.titel = titel;
        this.beschrijving = beschrijving;
        this.epicID = epicID;
    }
    public int getUserstoryID() {
        return userstoryID;
    }
    public String getTitel() {
        return titel;
    }
    public String getBeschrijving() {
        return beschrijving;
    }
    public int getEpicID() {
        return epicID;
    }
}
