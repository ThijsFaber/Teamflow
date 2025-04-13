import java.sql.Date;

public class Thread {
    private int id;
    private String titel;
    private Date datum;
    private boolean status;
    private int epicID;
    private int userStoryID;
    private int taakID;

    public Thread(int id, String titel, Date datum, boolean status, int epicID, int userStoryID, int taakID) {
        this.id = id;
        this.titel = titel;
        this.datum = datum;
        this.status = status;
        this.epicID = epicID;
        this.userStoryID = userStoryID;
        this.taakID = taakID;
    }

    // formatting van threads dus als (thread) word aangeroepen komt het er zo uit
    public String toString() {
        return "Thread #" + id + " - \"" + titel + "\" [" + datum + "]"
                + "\nStatus: " + (status ? "Actief" : "Inactief")
                + (epicID > 0 ? "\n - Epic ID: " + epicID : "")
                + (userStoryID > 0 ? "\n - User Story ID: " + userStoryID : "")
                + (taakID > 0 ? "\n - Taak ID: " + taakID : "")
                + "\n";
    }
}
