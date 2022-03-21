package plic.exceptions;

/**
 * @author unshade
 */
public class ErreurSemantique extends Throwable {

    /**
     * Constructeur des erreurs semantiques
     *
     * @param message message a passer en cas d'erreur
     */
    public ErreurSemantique(String message) {
        super("ERREUR: (Semantique) " + message);
    }
}
