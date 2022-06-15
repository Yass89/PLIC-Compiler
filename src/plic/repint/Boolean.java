package plic.repint;

import plic.exception.SemanticException;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class Boolean extends Operateur {

    private ArrayList<String> autorise = new ArrayList<>(Arrays.asList("Nombre", "Tableau", "Idf", "Entier"));

    @Override
    public void verifier() throws SemanticException {
        if (!autorise.contains(e1.getType()) || !autorise.contains(e2.getType())) {
            throw new SemanticException("Un operateur est non entier");
        }
        if (getNeg().equals("Moins"))
            throw new SemanticException("Le symbole moins ne peut pas etre applique a un boolean");
    }

    @Override
    public abstract String valeur();

    @Override
    public String getType() {
        return "Boolean";
    }

    @Override
    public String inverser() {
        if (!getNeg().equals(""))
            return "xor $v0, $v0, 1";
        else
            return "";
    }
}
