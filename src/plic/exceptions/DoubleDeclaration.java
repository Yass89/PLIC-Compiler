package plic.exceptions;

public class DoubleDeclaration extends Throwable {
    public DoubleDeclaration(String message) {
        super("ERREUR: " + message);
    }
}
