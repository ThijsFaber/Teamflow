import java.util.HashMap;
import java.util.Map;

public class ThreadRepository {
    private static Map<String, Thread> database = new HashMap<>();

    public static void save(Thread thread) {
        database.put(thread.getThreadId(), thread);
    }

    public static Thread findById(String threadId) {
        return database.get(threadId);
    }

    public static void update(Thread thread) {
        database.put(thread.getThreadId(), thread);
    }

    public static void delete(String threadId) {
        database.remove(threadId);
    }
}



