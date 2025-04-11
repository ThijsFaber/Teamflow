import com.teamflow.model.ScrumThread;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class ThreadController {

    public String startNieuweThread(String titel, String hoofdvraag, String auteurId) {
        return threadAanmaken(titel, hoofdvraag, auteurId);
    }

    public String threadAanmaken(String titel, String vraag, String auteurId) {
        if (titel == null || vraag == null || titel.isBlank() || vraag.isBlank()) {
            return "Fout: beide velden zijn verplicht.";
        }

        ScrumThread t = new ScrumThread();
        t.setThreadId(UUID.randomUUID().toString());
        t.setTitel(titel);
        t.setHoofdvraag(vraag);
        t.setAuteurId(auteurId);
        t.setDatum(LocalDateTime.now());
        t.setIsClosed(false);
        t.setIsArchived(false);

        try {
            ThreadRepository.save(t);
        } catch (Exception e) {
            return "Fout bij opslaan van thread: " + e.getMessage();
        }

        return "Thread succesvol aangemaakt";
    }

    public String sluitThread(String threadId, String userId) {
        ScrumThread t = ThreadRepository.findById(threadId);
        if (t == null) return "Thread niet gevonden.";
        if (!t.getAuteurId().equals(userId)) throw new UnauthorizedException();

        t.setIsClosed(true);
        ThreadRepository.update(t);
        return "Thread gesloten op " + LocalDateTime.now();
    }

    public String archiveerThread(String threadId, String userId) {
        ScrumThread t = ThreadRepository.findById(threadId);
        if (t == null) return "Thread niet gevonden.";
        if (!t.getAuteurId().equals(userId)) throw new UnauthorizedException();

        t.setIsArchived(true);
        ThreadRepository.update(t);
        return "Thread succesvol gearchiveerd.";
    }

    public String verwijderThread(String threadId, String userId) {
        ScrumThread t = ThreadRepository.findById(threadId);
        if (t == null) return "Thread niet gevonden.";
        if (!t.getAuteurId().equals(userId)) throw new UnauthorizedException();

        try {
            ThreadRepository.delete(threadId);
            AuditLogger.log("Thread verwijderd door " + userId + " op " + LocalDateTime.now());
        } catch (Exception e) {
            return "Verwijderen mislukt: " + e.getMessage();
        }

        return "Thread succesvol verwijderd.";
    }

    // (plaatsReactie, getReactiesVoorThread, pinReactie, unpinReactie kun je zelf later ook updaten op basis van ScrumThread.java)
}
