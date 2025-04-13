import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SprintService {

    // Retrieve active sprints
    public List<Sprint> getActiveSprints() throws SQLException {
        List<Sprint> sprints = new ArrayList<>();
        String sql = "SELECT * FROM Sprint WHERE Status = 1"; // Active sprints only
        try (Connection conn = Account.connect(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int sprintID = rs.getInt("SprintID");
                String naam = rs.getString("Naam");
                boolean status = rs.getBoolean("Status");
                Date datum = rs.getDate("Datum");
                sprints.add(new Sprint(sprintID, naam, status, datum));
            }
        }
        return sprints;
    }

    // Add a new sprint
    public void addSprint(String naamSprint, Date datum) throws SQLException {
        String query = "INSERT INTO Sprint (Naam, Datum, Status) VALUES (?, ?, ?)";
        try (Connection conn = Account.connect()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, naamSprint);
            // Set the current date if datum is not passed explicitly
            if (datum == null) {
                datum = Date.valueOf(LocalDate.now());
            }
            stmt.setDate(2, datum);
            stmt.setInt(3,1);
            stmt.executeUpdate();
        }
    }

    // Add a new Epic
    public void addEpic(String title, String description) throws SQLException {
        System.out.println("Saving Epic to database...");
        String sql = "INSERT INTO Epics (titel, beschrijving) VALUES (?, ?)";
        try (Connection conn = Account.connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, title);
            stmt.setString(2, description);
            stmt.executeUpdate();
        }
    }

    public void addUserStory(String title, String description, int epicID) throws SQLException {
        System.out.println("Saving User Story to database...");
        String sql = "INSERT INTO UserStories (titel, beschrijving, epic_ID) VALUES (?, ?, ?)";
        try (Connection conn = Account.connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, title);
            stmt.setString(2, description);
            stmt.setInt(3, epicID);
            stmt.executeUpdate();
        }
    }

    public void addTask(String title, String description, int userStoryID) throws SQLException {
        System.out.println("Saving taak to database...");
        String sql = "INSERT INTO Taken (titel, beschrijving, userStory_ID) VALUES (?, ?, ?)";
        try (Connection conn = Account.connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, title);
            stmt.setString(2, description);
            stmt.setInt(3, userStoryID);
            stmt.executeUpdate();
        }
    }


    public void userAddSprint(int SprintID, int gebruikersID) throws SQLException {
        String sql = "INSERT INTO `scrumassistant`.`sprint_teamleden`(`SprintID`,`GebruikerID`)VALUES(?,?)";
        try (Connection conn = Account.connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, SprintID);
            stmt.setInt(2, gebruikersID);
            stmt.executeUpdate();
            System.out.print("Gebruiker toegevoegd aan sprint");
        }
    }
}

