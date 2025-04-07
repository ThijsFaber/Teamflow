import java.time.LocalDateTime;


public class Thread {
    private String threadId;
    private String titel;
    private String hoofdvraag;
    private String auteurId;
    private LocalDateTime datum;
    private boolean isClosed;
    private boolean isArchived;

    // Setters
    public void setThreadId(String threadId) { this.threadId = threadId; }
    public void setTitel(String titel) { this.titel = titel; }
    public void setHoofdvraag(String hoofdvraag) { this.hoofdvraag = hoofdvraag; }
    public void setAuteurId(String auteurId) { this.auteurId = auteurId; }
    public void setDatum(LocalDateTime datum) { this.datum = datum; }
    public void setIsClosed(boolean isClosed) { this.isClosed = isClosed; }
    public void setIsArchived(boolean isArchived) { this.isArchived = isArchived; }

    // Getters
    public String getThreadId() { return threadId; }
    public String getTitel() { return titel; }
    public String getHoofdvraag() { return hoofdvraag; }
    public String getAuteurId() { return auteurId; }
    public LocalDateTime getDatum() { return datum; }
    public boolean isClosed() { return isClosed; }
    public boolean isArchived() { return isArchived; }
}


