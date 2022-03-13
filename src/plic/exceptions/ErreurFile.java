package plic.exceptions;

public class ErreurFile extends Throwable{
    public ErreurFile(String message) {
        super("ERREUR: " + message);
    }
}
