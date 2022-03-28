package plic.repint;

import plic.exceptions.ErreurSemantique;

public class Acces extends Expression {

    private Idf idf;

    public Acces(Idf idf) {
        this.idf = idf;
    }

    @Override
    public void verifier() throws ErreurSemantique {
        idf.verifier();
    }

    @Override
    public Object getVal() {
        return idf;
    }

    public String toMIPS() {
        return " ";
    }

    public Idf getIdf() {
        return idf;
    }
}
