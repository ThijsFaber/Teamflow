import java.util.ArrayList;
import java.util.Date;

public class ChatThread {
    private int threadID;
    private boolean status;
    private String titel;
    private Date datum;

    // koppelingen
    private Epics epic;
    private UserStories userStory;
    private Taken taak;

    // reacties
    private ArrayList<Bericht> reacties = new ArrayList<>();

    // gepind bericht
    private Bericht gepindBericht;

    // constructors
    public ChatThread(int threadID, boolean status, String titel, Date datum, Epics epic) {
        this.threadID = threadID;
        this.status = status;
        this.titel = titel;
        this.datum = datum;
        this.epic = epic;
    }

    public ChatThread(int threadID, boolean status, String titel, Date datum, Epics epic, UserStories userStory) {
        this(threadID, status, titel, datum, epic);
        this.userStory = userStory;
    }

    public ChatThread(int threadID, boolean status, String titel, Date datum, Epics epic, UserStories userStory, Taken taak) {
        this(threadID, status, titel, datum, epic, userStory);
        this.taak = taak;
    }

    // getter
    public int getThreadID() {
        return threadID;
    }

    public boolean isStatus() {
        return status;
    }

    public String getTitel() {
        return titel;
    }

    public Date getDatum() {
        return datum;
    }

    public Epics getEpic() {
        return epic;
    }

    public UserStories getUserStory() {
        return userStory;
    }

    public Taken getTaak() {
        return taak;
    }

    public ArrayList<Bericht> getReacties() {
        return reacties;
    }

    public Bericht getGepindBericht() {
        return gepindBericht;
    }

    public boolean heeftGepindBericht() {
        return gepindBericht != null;
    }

    // setter
    public void setThreadID(int threadID) {
        this.threadID = threadID;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public void setEpic(Epics epic) {
        this.epic = epic;
    }

    public void setUserStory(UserStories userStory) {
        this.userStory = userStory;
    }

    public void setTaak(Taken taak) {
        this.taak = taak;
    }

    public void setGepindBericht(Bericht bericht) {
        this.gepindBericht = bericht;
    }

    // methode om te kijken waar een thread aan gekoppeld is
    public String getGekoppeldAan() {
        if (taak != null) {
            return "Taak: " + taak.getTaakTitel();
        } else if (userStory != null) {
            return "UserStory: " + userStory.getUserstoryTitel();
        } else if (epic != null) {
            return "Epic: " + epic.getEpicTitel();
        } else {
            return "Geen scrumelement gekoppeld.";
        }
    }

    public void voegReactieToe(Bericht reactie) {
        reacties.add(reactie);
    }
}
