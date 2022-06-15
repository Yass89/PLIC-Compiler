package plic.exception;

public class AtLeastOneInstructionException extends SyntaxException {
    public AtLeastOneInstructionException() {
        super("Le bloc doit contenir au moins une instruction");
    }
}
