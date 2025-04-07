public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
        super("Gebruiker is niet gemachtigd.");
    }
}


