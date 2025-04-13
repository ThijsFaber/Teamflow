import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Sprint {
    private int sprintID;
    private String naam;
    private boolean status;
    private Date datum;

    public Sprint(int sprintID, String naam, boolean status, Date datum) {
        this.sprintID = sprintID;
        this.naam = naam;
        this.status = status;
        this.datum = datum;
    }

    public int getSprintID() {
        return sprintID;
    }

    public String getNaam() {
        return naam;
    }

    public boolean isActive() {
        return status;
    }

    public Date getDatum() {
        return datum;
    }

    public long getDaysLeft() {
        LocalDate startDate = datum.toLocalDate();
        LocalDate endDate = startDate.plusWeeks(2);  // Sprint lasts 2 weeks
        return ChronoUnit.DAYS.between(LocalDate.now(), endDate);
    }

    @Override
    public String toString() {
        return "Sprint #" + sprintID + " | Naam: " + naam + " | Datum: " + datum + " | Actief: " + (status ? "Ja" : "Nee") + " | Tijd over: " + getDaysLeft() + " dagen";
    }
}
