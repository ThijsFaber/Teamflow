import java.sql.Date;

public class ThreadReactie {
    private int reactieID;
    private ChatThread thread;  // kopeling met  chatthread object
    private int userID;
    private String inhoud;
    private Date datum;

    // Constructor voor nieuwe reactie
    public ThreadReactie(ChatThread thread, int userID, String inhoud, Date datum) {
        this.thread = thread;
        this.userID = userID;
        this.inhoud = inhoud;
        this.datum = datum;
    }

    // Constructor met id  bij het ophalen van een reactie uit de database
    public ThreadReactie(int reactieID, ChatThread thread, int userID, String inhoud, Date datum) {
        this.reactieID = reactieID;
        this.thread = thread;
        this.userID = userID;
        this.inhoud = inhoud;
        this.datum = datum;
    }

    // Getters
    public int getReactieID() { return reactieID; }
    public ChatThread getThread() { return thread; }
    public int getThreadID() { return thread.getThreadID(); }
    public int getUserID() { return userID; }
    public String getInhoud() { return inhoud; }
    public Date getDatum() { return datum; }

    // SQL-query  voor database
    public String buildInsertQuery() {
        return "INSERT INTO thread_reactie (ThreadID, UserID, Inhoud, Datum) VALUES (" +
                getThreadID() + ", " + userID + ", \"" + inhoud + "\", \"" + datum + "\")";
    }
}
