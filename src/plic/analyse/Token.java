package plic.analyse;

public class Token {
    private static final int LIGNE_DEPART = 1;
    private int numLigne;
    public Token() {
        numLigne = LIGNE_DEPART;
    }
    public void augmenterLigne() {
        numLigne++;
    }
    public int getNumLigne() {
        return numLigne;
    }
}
