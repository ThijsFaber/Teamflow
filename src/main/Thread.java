import java.time.LocalDateTime;
import java.util.UUID;

public class Thread {
    private String threadId;
    private String titel;
    private String hoofdVraag;
    private String auteurId;
    private LocalDateTime datum;
    private boolean isClosed = false;
    private boolean isArchived = false;

    public Thread(String threadId, String titel, String hoofdVraag, String auteurId, LocalDateTime datum) {
        this.threadId = threadId;
        this.titel = titel;
        this.hoofdVraag = hoofdVraag;
        this.auteurId = auteurId;
        this.datum = datum;
    }

    public void setIsClosed(boolean status) {
        this.isClosed = status;
    }

    public void setIsArchived(boolean status) {
        this.isArchived = status;
    }

    public String getAuteurId() {
        return auteurId;
    }
    public String getThreadId(){return threadId;}
    public boolean isClosed(){return isClosed;}
}


