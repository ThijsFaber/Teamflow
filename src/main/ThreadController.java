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

        Thread t = new Thread(UUID.randomUUID().toString(), titel, vraag, auteurId, LocalDateTime.now());


        try {
            ThreadRepository.save(t);
        } catch (Exception e) {
            return "Fout bij opslaan van thread: " + e.getMessage();
        }

        return "Thread succesvol aangemaakt";
    }

    public String sluitThread(String threadId, String userId) {
        Thread t = ThreadRepository.findById(threadId);
        if (t == null) return "Thread niet gevonden.";
        if (!t.getAuteurId().equals(userId)) throw new UnauthorizedException();

        t.setIsClosed(true);
        ThreadRepository.update(t);
        return "Thread gesloten op " + LocalDateTime.now();
    }

    public String archiveerThread(String threadId, String userId) {
        Thread t = ThreadRepository.findById(threadId);
        if (t == null) return "Thread niet gevonden.";
        if (!t.getAuteurId().equals(userId)) throw new UnauthorizedException();

        t.setIsArchived(true);
        ThreadRepository.update(t);
        return "Thread succesvol gearchiveerd.";
    }

    public String verwijderThread(String threadId, String userId) {
        Thread t = ThreadRepository.findById(threadId);
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

    public String plaatsReactie(String threadId, String inhoud, String userId) {
        Thread t = ThreadRepository.findById(threadId);
        if (t == null) return "Thread bestaat niet.";
        if (inhoud == null || inhoud.isBlank()) return "Inhoud verplicht.";
        if (t.isClosed()) return "Deze thread is gesloten. Reacties zijn niet meer mogelijk.";

        Reactie r = new Reactie();
        r.setReactieId(UUID.randomUUID().toString());
        r.setThreadId(threadId);
        r.setUserId(userId);
        r.setInhoud(inhoud);
        r.setDatum(LocalDateTime.now());
        r.setPinned(false);

        try {
            ReactieRepository.save(r);
        } catch (Exception e) {
            return "Fout bij opslaan van reactie: " + e.getMessage();
        }

        return "Reactie geplaatst";
    }

    public List<Reactie> getReactiesVoorThread(String threadId) {
        Reactie gepind = ReactieRepository.findPinnedByThreadId(threadId);
        List<Reactie> reacties = ReactieRepository.findByThreadIdSorted(threadId);

        if (gepind != null) {
            reacties.remove(gepind);
            reacties.add(0, gepind);
        }

        return reacties;
    }

    public String pinReactie(String threadId, String reactieId, String userId) {
        Thread t = ThreadRepository.findById(threadId);
        if (t == null) return "Thread niet gevonden.";
        if (!t.getAuteurId().equals(userId)) throw new UnauthorizedException();

        List<Reactie> reacties = ReactieRepository.findByThreadIdSorted(threadId);
        for (Reactie r : reacties) {
            if (r.isPinned()) {
                r.setPinned(false);
                ReactieRepository.update(r);
            }
        }

        Reactie tePin = ReactieRepository.findById(reactieId);
        if (tePin == null) return "Reactie niet gevonden.";

        tePin.setPinned(true);
        ReactieRepository.update(tePin);
        return "Nieuw antwoord gepind op " + LocalDateTime.now();
    }

    public String unpinReactie(String reactieId, String userId) {
        Reactie r = ReactieRepository.findById(reactieId);
        if (r == null) return "Reactie niet gevonden.";

        Thread t = ThreadRepository.findById(r.getThreadId());
        if (t == null) return "Thread niet gevonden.";
        if (!t.getAuteurId().equals(userId)) throw new UnauthorizedException();

        r.setPinned(false);
        ReactieRepository.update(r);
        return "Reactie ontpind";
    }
}

