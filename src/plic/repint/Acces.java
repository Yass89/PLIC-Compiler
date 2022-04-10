package plic.repint;

import plic.exceptions.ErreurSemantique;

public class Acces extends Operande {

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
        return null;
    }

    @Override
    public String getType() {
        return null;
    }

    public String getNom() {
        return idf.getNom();
    }
}
