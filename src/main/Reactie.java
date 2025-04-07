import java.time.LocalDateTime;


public class Reactie {
    private String reactieId;
    private String threadId;
    private String userId;
    private String inhoud;
    private LocalDateTime datum;
    private boolean isPinned;

    // Setters
    public void setReactieId(String reactieId) { this.reactieId = reactieId; }
    public void setThreadId(String threadId) { this.threadId = threadId; }
    public void setUserId(String userId) { this.userId = userId; }
    public void setInhoud(String inhoud) { this.inhoud = inhoud; }
    public void setDatum(LocalDateTime datum) { this.datum = datum; }
    public void setPinned(boolean isPinned) { this.isPinned = isPinned; }

    // Getters
    public String getReactieId() { return reactieId; }
    public String getThreadId() { return threadId; }
    public String getUserId() { return userId; }
    public String getInhoud() { return inhoud; }
    public LocalDateTime getDatum() { return datum; }
    public boolean isPinned() { return isPinned; }
}
