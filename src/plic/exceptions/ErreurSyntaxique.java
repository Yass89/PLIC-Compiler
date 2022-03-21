package plic.exceptions;

/**
 * @author unshade
 */
public class ErreurSyntaxique extends Throwable {

    /**
     * Constructeur des erreurs syntaxiques
     *
     * @param message message a passer en cas d'erreur
     */
    public ErreurSyntaxique(String message) {
        super("ERREUR: (Syntaxique) " + message);
    }
}
