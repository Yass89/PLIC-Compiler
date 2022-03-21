package plic.exceptions;

/**
 * @author unshade
 */
public class DoubleDeclaration extends Throwable {

    /**
     * Constructeur de l'erreur de double declaration
     *
     * @param message message a passer en cas d'erreur
     */
    public DoubleDeclaration(String message) {
        super("ERREUR: " + message);
    }
}
