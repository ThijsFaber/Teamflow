import java.sql.*;
import java.time.LocalDate;
public class MessageService {

    // Methode om berichten weer te geven voor een specifieke sprint
    private void displayMessages(String query, int id) {
        try (Connection connection = Account.connect();
             PreparedStatement stmt = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                System.out.println("Geen berichten gevonden voor ID: " + id);
                return;
            }

            rs.beforeFirst();

            while (rs.next()) {
                String naam = rs.getString("Naam");
                String rol = rs.getString("Rol");
                String epicTitel = rs.getString("EpicTitel");
                String epicBeschrijving = rs.getString("EpicBeschrijving");
                String userStoryTitel = rs.getString("UserStoryTitel");
                String userStoryBeschrijving = rs.getString("UserStoryBeschrijving");
                String taakTitel = rs.getString("TaakTitel");
                String taakBeschrijving = rs.getString("TaakBeschrijving");
                String tekst = rs.getString("Tekst");
                Date datum = rs.getDate("Datum");

                System.out.println("Naam: " + naam);
                System.out.println("Rol: " + rol);
                if (epicTitel != null || userStoryTitel != null || taakTitel != null) {
                    if (epicTitel != null) {
                        System.out.println("Epic titel: " + epicTitel);
                        System.out.println("Epic beschrijving: " + epicBeschrijving);
                    }
                    if (userStoryTitel != null) {
                        System.out.println("User Story titel: " + userStoryTitel);
                        System.out.println("User Story beschrijving: " + userStoryBeschrijving);
                    }
                    if (taakTitel != null) {
                        System.out.println("Taak titel: " + taakTitel);
                        System.out.println("Taak beschrijving: " + taakBeschrijving);
                    }
                }
                System.out.println("Tekst: " + tekst);
                System.out.println("Datum: " + datum);
                System.out.println("------------------------------------");
            }
        } catch (SQLException e) {
            System.err.println("Fout bij het ophalen van berichten: " + e.getMessage());
        }
    }


    // Methode om een bericht te versturen voor een specifieke sprint en optioneel te koppelen aan Scrum-elementen
    public void sendMessage(int gebruikerID, int sprintID, String tekst, int epicID, int userStoryID, int taakID) {
        String messageInsert = "INSERT INTO Bericht (Tekst, Datum, AfzenderID, epic_id, UserStory_ID, taak_id) VALUES (?, ?, ?, ?, ?, ?)";
        String sprintLinkInsert = "INSERT INTO Sprint_Bericht_Verbinding (BerichtID, SprintID) VALUES (?, ?)";

        try (Connection connection = Account.connect()) {
            connection.setAutoCommit(false); // Start een transactie

            try (PreparedStatement stmt = connection.prepareStatement(messageInsert, PreparedStatement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, tekst);
                stmt.setDate(2, Date.valueOf(LocalDate.now()));
                stmt.setInt(3, gebruikerID);

                // Koppel Scrum-elementen, of zet null als ze niet opgegeven zijn
                if (epicID != 0) {
                    stmt.setInt(4, epicID);
                } else {
                    stmt.setNull(4, java.sql.Types.INTEGER);
                }

                if (userStoryID != 0) {
                    stmt.setInt(5, userStoryID);
                } else {
                    stmt.setNull(5, java.sql.Types.INTEGER);
                }

                if (taakID != 0) {
                    stmt.setInt(6, taakID);
                } else {
                    stmt.setNull(6, java.sql.Types.INTEGER);
                }

                int affectedRows = stmt.executeUpdate();

                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int berichtID = generatedKeys.getInt(1);

                            // Koppel het bericht aan de sprint
                            try (PreparedStatement sprintStmt = connection.prepareStatement(sprintLinkInsert)) {
                                sprintStmt.setInt(1, berichtID);
                                sprintStmt.setInt(2, sprintID);
                                sprintStmt.executeUpdate();
                            }
                        }
                    }
                }

                connection.commit();
                System.out.println("Bericht succesvol verzonden inclusief Scrum-elementen.");
            } catch (SQLException e) {
                connection.rollback();
                System.err.println("Fout bij het verzenden van bericht: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Fout bij het verbinden met de database: " + e.getMessage());
        }
    }

    public void displayMessagesForSprint(int sprintID) {
        String query = "SELECT b.Tekst, b.Datum, g.Naam, g.Rol, e.Titel AS EpicTitel, e.Beschrijving AS EpicBeschrijving, " +
                "us.Titel AS UserStoryTitel, us.Beschrijving AS UserStoryBeschrijving, t.Titel AS TaakTitel, t.Beschrijving AS TaakBeschrijving " +
                "FROM Bericht b " +
                "JOIN Gebruiker g ON b.AfzenderID = g.GebruikerID " +
                "LEFT JOIN Epics e ON b.Epic_ID = e.EpicID " +
                "LEFT JOIN UserStories us ON b.UserStory_ID = us.UserStoryID " +
                "LEFT JOIN Taken t ON b.Taak_ID = t.TaakID " +
                "JOIN Sprint_Bericht_Verbinding sbv ON b.BerichtID = sbv.BerichtID " +
                "WHERE sbv.SprintID = ?";
        displayMessages(query, sprintID);
    }


    // Methode om berichten weer te geven in een specifieke thread
    public void displayMessagesInThread(int threadID) {
        String query = "SELECT b.Tekst, b.Datum, g.Naam, g.Rol, e.Titel AS EpicTitel, e.Beschrijving AS EpicBeschrijving, " +
                "us.Titel AS UserStoryTitel, us.Beschrijving AS UserStoryBeschrijving, t.Titel AS TaakTitel, t.Beschrijving AS TaakBeschrijving " +
                "FROM Bericht b " +
                "JOIN Gebruiker g ON b.AfzenderID = g.GebruikerID " +
                "LEFT JOIN Epics e ON b.Epic_ID = e.EpicID " +
                "LEFT JOIN UserStories us ON b.UserStory_ID = us.UserStoryID " +
                "LEFT JOIN Taken t ON b.Taak_ID = t.TaakID " +
                "WHERE b.Thread_ID = ?";
        displayMessages(query, threadID);
    }


    // Methode om een bericht naar een thread te sturen
    public void sendMessageToThread(int gebruikerID, int threadID, String tekst, int epicID, int userStoryID, int taakID) throws SQLException {
        String sql = "INSERT INTO Bericht (Tekst, Datum, AfzenderID, Thread_ID, Epic_ID, UserStory_ID, Taak_ID) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Account.connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tekst);
            stmt.setDate(2, Date.valueOf(LocalDate.now()));
            stmt.setInt(3, gebruikerID);
            stmt.setInt(4, threadID);
            if (epicID > 0) {
                stmt.setInt(5, epicID);
            } else {
                stmt.setNull(5, java.sql.Types.INTEGER);
            }
            if (userStoryID > 0) {
                stmt.setInt(6, userStoryID);
            } else {
                stmt.setNull(6, java.sql.Types.INTEGER);
            }
            if (taakID > 0) {
                stmt.setInt(7, taakID);
            } else {
                stmt.setNull(7, java.sql.Types.INTEGER);
            }

            stmt.executeUpdate();
            System.out.println("Bericht succesvol geplaatst in thread " + threadID);
        }
    }
}