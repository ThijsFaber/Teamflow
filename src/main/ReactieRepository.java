import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Comparator;

public class ReactieRepository {

    private static Map<String, Reactie> database = new HashMap<>();

    public static void save(Reactie reactie) {
        database.put(reactie.getReactieId(), reactie);
    }

    public static Reactie findById(String reactieId) {
        return database.get(reactieId);
    }

    public static List<Reactie> findByThreadIdSorted(String threadId) {
        List<Reactie> result = new ArrayList<>();
        for (Reactie r : database.values()) {
            if (r.getThreadId().equals(threadId)) {
                result.add(r);
            }
        }
        result.sort(Comparator.comparing(Reactie::getDatum));
        return result;
    }

    public static Reactie findPinnedByThreadId(String threadId) {
        for (Reactie r : database.values()) {
            if (r.getThreadId().equals(threadId) && r.isPinned()) {
                return r;
            }
        }
        return null;
    }

    public static void update(Reactie reactie) {
        database.put(reactie.getReactieId(), reactie);
    }
}


