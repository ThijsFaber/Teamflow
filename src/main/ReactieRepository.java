import com.teamflow.model.ScrumThread;
import java.util.HashMap;
import java.util.Map;

public class ReactieRepository {
    private static Map<String, ScrumThread> database = new HashMap<>();

    public static void save(ScrumThread thread) {
        database.put(thread.getThreadId(), thread);
    }

    public static ScrumThread findById(String threadId) {
        return database.get(threadId);
    }

    public static void update(ScrumThread thread) {
        database.put(thread.getThreadId(), thread);
    }

    public static void delete(String threadId) {
        database.remove(threadId);
    }
}
