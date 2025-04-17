import java.util.*;

public class MainZoekfunctie { //Dit is een aparte main om puur dit te testen
    public static void main(String[] args) {

        List<Zoekbaar> data = new ArrayList<>(); //Zoekbaar is die interface die de title en inhoud opvraagt
        data.add(new Zoekbaar() {
            public String getTitel() {
                return "Sprintplanning"; // op basis hiervan wordt het opgezocht..niet gekoppeld met echte klasse dus
            }
            public String getInhoud() {
                return "Vandaag bespreken we de taken van de userstories.";
            }
            public String toString() {
                return " Vandaag bespreken we de taken en doelen.";
            }
        });

        data.add(new Zoekbaar() {
            public String getTitel() {
                return "Epic"; //hetzelfde als boven
            }
            public String getInhoud() {
                return "User story van Epic 3 lijkt op die van Epic .";
            }
            public String toString() {
                return "User story van Epic 3 lijkt op die van Epic .";
            }
        });

        Scanner scanner = new Scanner(System.in);
        System.out.print("Typ trefwoord om te zoeken: ");
        String trefwoord = scanner.nextLine();

        ZoekService.zoek(data, trefwoord);
    }
}
