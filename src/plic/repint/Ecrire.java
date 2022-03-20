package plic.repint;

import plic.exceptions.ErreurSemantique;

public class Ecrire extends Instruction{

    Expression e;

    public Ecrire(Expression e) {
        this.e = e;
    }

    @Override
    public String toString() {
        return "Ecrire{" +
                "e=" + e +
                '}';
    }

    @Override
    public void verifier() throws ErreurSemantique {
        e.verifier();
    }

    @Override
    public String toMips() {
        return e.toMips();
    }
}
