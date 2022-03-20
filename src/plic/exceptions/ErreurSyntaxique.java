package plic.exceptions;

public class ErreurSyntaxique extends Throwable {
    public ErreurSyntaxique(String attendu) {
        super("ERREUR: (Syntaxique) " + attendu);
    }
}
