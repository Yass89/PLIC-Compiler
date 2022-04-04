package plic.repint;

import plic.exceptions.ErreurSemantique;

public class Acces extends Expression {

    private Idf idf;
    private Expression expression;

    public Acces(Idf idf) {
        this.idf = idf;
        this.expression = null;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    public String toString() {
        return "" + idf;
    }


    @Override
    public void verifier() throws ErreurSemantique {
        Symbole s = TDS.getInstance().identifier(new Entree(idf.getNom()));
        idf.verifier();
        if (expression != null) {
            expression.verifier();
        }

    }

    @Override
    public String toMips() {
        if (expression == null) {
            return "la $a0, " + idf.toMips()+"\n";
        }
        else {
            Entree e = new Entree(idf.getNom());
            Symbole s = TDS.getInstance().identifier(e);
            if(expression instanceof Nombre) {
                return "la $a0, " + (s.getDeplacement()-((Integer)expression.getVal())*4) + "\n"
                        + "li $t0, " + this.expression.toMips() + "\n"
                        + "blt $t0, 0, end\n"
                        + "bge $t0, "+s.getTaille()+", end\n";
            }
            else {
                return this.expression.toMips() +
                        "li $t1,4\n" +
                        "lw $t0,($a0)\n" +
                        "blt $t0, 0, end\n"
                        + "bge $t0, "+s.getTaille()+", end\n"
                        + "mult $t0,$t1\n"
                        + "mflo $t0\n"
                        + "la $a0, " + (s.getDeplacement()) + "\n"
                        + "sub $a0,$a0,$t0\n";

            }
        }
    }

    @Override
    public Object getVal() {
        return null;
    }

    public String getNom() {
        return idf.getNom();
    }
}
