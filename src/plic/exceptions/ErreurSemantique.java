package plic.exceptions;

public class ErreurSemantique extends Throwable{
    public ErreurSemantique(String attendu) {
        super("ERREUR: (Semantique) " + attendu);
    }
}
