package plic.repint;


import plic.exception.SemanticException;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class Decimal extends Operateur {

    private ArrayList<String> autorise = new ArrayList<>(Arrays.asList("Nombre", "Tableau", "Idf","Entier"));

    @Override
    public void verifier() throws SemanticException {
        if (!autorise.contains(e1.getType()) || !autorise.contains(e2.getType())) {
            throw new SemanticException("Un operateur est non entier");
        }
        if (getNeg().equals("Non"))
            throw new SemanticException("Le symbole non ne peut pas etre applique a un decimal");
    }

    @Override
    public abstract String valeur();

    @Override
    public String getType() {
        return "Entier";
    }

    @Override
    public String inverser() {
        if (!getNeg().equals(""))
            return "mul $v0, $v0, -1";
        else
            return "";
    }

    protected String stackOverflow() {
        return "\t#suite du test stack overflow\n\tsgt $t3, $zero, $v0\n" +
                "\txor $t1, $t1, $t2\n" +
                "\tbne $t3, $t1, stackOverflowException\n";
    }
}
