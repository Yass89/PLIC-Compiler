package plic.repint;

import plic.exception.SemanticException;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class CompletelyBoolean extends Boolean {

    private ArrayList<String> autorise = new ArrayList<>(Arrays.asList("Boolean"));

    @Override
    public void verifier() throws SemanticException {
        if (!autorise.contains(e1.getType()) || !autorise.contains(e2.getType())) {
            throw new SemanticException("Un operateur est non boolean");
        }
    }

    @Override
    public abstract String valeur();
}
