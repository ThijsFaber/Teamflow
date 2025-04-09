public class UserStories extends Epics{
    protected int userstoryID;
    protected String userstoryTitel;
    protected String userstoryBeschrijving;


    public UserStories(int userstoryID, String titel, String beschrijving, int epicID, String epicTitel, String epicBeschrijving) {
        super(epicID, epicTitel, epicBeschrijving);
        this.userstoryID = userstoryID;
        this.userstoryTitel = titel;
        this.userstoryBeschrijving = beschrijving;
    }

    public int getUserstoryID() {
        return userstoryID;
    }
    public String getUserstoryTitel() {
        return userstoryTitel;
    }
    public String getUserstoryBeschrijving() {
        return userstoryBeschrijving;
    }
}
