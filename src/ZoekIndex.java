import java.time.LocalDateTime;

public class ZoekIndex {
    private int zoekID;
    private String type;
    private int objectID;
    private String titel;
    private String inhoud;
    private int afzenderID;
    private LocalDateTime datum;

    // constructor
    public ZoekIndex(int zoekID, String type, int objectID, String titel, String inhoud, int afzenderID, LocalDateTime datum) {
        this.zoekID = zoekID;
        this.type = type;
        this.objectID = objectID;
        this.titel = titel;
        this.inhoud = inhoud;
        this.afzenderID = afzenderID;
        this.datum = datum;
    }

    // Getters
    public int getZoekID() {
        return zoekID;
    }

    public String getType() {
        return type;
    }

    public int getObjectID() {
        return objectID;
    }

    public String getTitel() {
        return titel;
    }

    public String getInhoud() {
        return inhoud;
    }

    public int getAfzenderID() {
        return afzenderID;
    }

    public LocalDateTime getDatum() {
        return datum;
    }

    // Setters
    public void setType(String type) {
        this.type = type;
    }

    public void setObjectID(int objectID) {
        this.objectID = objectID;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public void setInhoud(String inhoud) {
        this.inhoud = inhoud;
    }

    public void setAfzenderID(int afzenderID) {
        this.afzenderID = afzenderID;
    }

    public void setDatum(LocalDateTime datum) {
        this.datum = datum;
    }
}
