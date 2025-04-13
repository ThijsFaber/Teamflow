import java.sql.Connection; //verbinden met dtabase
import java.sql.Date; //datum van reactie opslaan
import java.sql.SQLException;//als iets misgaat (met database)
import java.time.LocalDate; //de huidige datum
import java.util.List;


//class voor het ophalen en plaatsen van reacties
public class ThreadReactieService {
    private final ThreadReactieDAO reactieDAO;// reacties voor databse
    private final ThreadDAO threadDAO; //controleren of thread nog open is

    //constructor  voor verbinding met databse
    public ThreadReactieService(Connection conn) {
        this.reactieDAO = new ThreadReactieDAO(conn);
        this.threadDAO = new ThreadDAO(conn);
    }

    //nieuwe ractie toevoegen
    public void plaatsReactie(int threadID, int userID, String inhoud) throws SQLException {
        if (inhoud == null || inhoud.trim().isEmpty()) { //inhoud van reactie
            throw new IllegalArgumentException("Inhoud mag niet leeg zijn.");
        }//checken of inhoud juist ingevuld is


        //Thread object
        Thread thread = threadDAO.getThreadByID(threadID);

        if (thread == null) {// geen thread? melding
            throw new IllegalArgumentException("De opgegeven thread bestaat niet.");
        }

        //reageren aleen op threads die nog open zijn
        if (thread.isClosed()) {
            throw new IllegalStateException("Deze thread is gesloten. Reacties zijn niet meer mogelijk.");
        }

        //reactie opslaan imn databse en opgehaald door ThreadReactieDAO
        ThreadReactie nieuweReactie = new ThreadReactie(0, threadID, userID, inhoud, Date.valueOf(LocalDate.now()));
        reactieDAO.voegReactieToe(nieuweReactie);
    }

    public List<ThreadReactie> haalReactiesOp(int threadID) throws SQLException {
        return reactieDAO.getReactiesVoorThread(threadID);
    }
}
