package plic.repint;

import plic.exceptions.ErreurSemantique;

public class Affectation extends Instruction {

    Expression e;
    Idf idf;

    public Affectation(Expression e, Idf idf) {
        this.e = e;
        this.idf = idf;
    }

    @Override
    public String toString() {
        return "Affectation{" +
                "e=" + e +
                ", idf=" + idf +
                '}';
    }

    @Override
    public void verifier() throws ErreurSemantique {
        idf.verifier();
        e.verifier();
    }
}
