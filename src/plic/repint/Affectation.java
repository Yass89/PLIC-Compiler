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

    @Override
    public String toMips() {
        Entree entree = new Entree(idf.getNom());
        Symbole symbole = TDS.getInstance().identifier(entree);
        StringBuilder mips = new StringBuilder();
        if (e instanceof Idf) {
            Entree entree2 = new Entree((String) e.getVal());
            Symbole s2 = TDS.getInstance().identifier(entree2);
            mips.append("lw $v0,").append(s2.getDeplacement()).append("($sp)").append("\n").append("sw $v0,").append(symbole.getDeplacement()).append("($sp)").append("\n");
        } else
            mips.append("li $v0,").append(e.getVal()).append("\n").append("sw $v0,").append(symbole.getDeplacement()).append("($sp)\n");
        return mips.toString();
    }
}
