package plic.repint;

import plic.exceptions.ErreurSemantique;

public class Acces extends Expression{

    private Idf idf;
    private Expression expression;

    public Acces(Idf idf) {
        this.idf = idf;
        this.expression = null;
    }


    public String toString() {
        return ""+idf;
    }


    @Override
    public void verifier() throws ErreurSemantique {

    }

    @Override
    public String toMips() {
        return null;
    }

    @Override
    public Object getVal() {
        return null;
    }

    public String getNom() {
        return idf.getNom();
    }
}
