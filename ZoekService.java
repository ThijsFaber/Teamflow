import java.util.List;

public class ZoekService {
    public static void zoek(List<Zoekbaar> items, String trefwoord) {

        // print het  naar de console
        System.out.println("\nZoekresultaten voor: '" + trefwoord + "'\n");
        boolean gevonden = false;

        for (Zoekbaar z : items) { //al de objecten hebben nu de methoden getTitel en getInhoud.
            if (z.getTitel().toLowerCase().contains(trefwoord.toLowerCase()) ||
                    z.getInhoud().toLowerCase().contains(trefwoord.toLowerCase())) {
                System.out.println(z);
                gevonden = true;
            }
        }

        if (!gevonden) {
            System.out.println("Geen resultaten gevonden.");
        }
    }
}
