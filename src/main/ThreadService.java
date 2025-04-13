import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;



public class ThreadService {
    private final ThreadDAO threadDAO;


    //Constructor voor databaseverbinding ->maakt ThreadDAO aan
    public ThreadService(Connection conn) {
        this.threadDAO = new ThreadDAO(conn);
    }


    //new thread aanmaken
    public void maakNieuweThread(String titel, String inhoud, int scrumID, int auteurID) throws IllegalArgumentException, SQLException {
        if (titel == null || titel.isEmpty() || inhoud == null || inhoud.isEmpty()) {
            throw new IllegalArgumentException("Titel en inhoud mogen niet leeg zijn.");
        }

        Thread nieuweThread = new Thread(
                0, // door DB gegenereerd
                titel,
                inhoud,
                true, // status actief
                scrumID,
                auteurID,
                Date.valueOf(LocalDate.now()),
                false, // isClosed
                false  // isArchived
        );

        threadDAO.voegThreadToe(nieuweThread); // thread doorsturen naar de database
    }
}

