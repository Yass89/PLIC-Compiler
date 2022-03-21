package plic.exceptions;

/**
 * @author unshade
 */
public class ErreurFile extends Throwable {

    /**
     * Constructeur de l'erreur de fichier
     *
     * @param message message a passer en cas d'erreur
     */
    public ErreurFile(String message) {
        super("ERREUR: (Erreur Fichier) " + message);
    }
}
