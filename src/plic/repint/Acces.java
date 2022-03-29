package plic.repint;

import plic.exceptions.ErreurSemantique;

public class Acces extends Expression{

    private Idf idf;
    private Expression expression;

    public Acces(Idf idf) {
        this.idf = idf;
        this.expression = null;
    }

    public Idf getIdf() {
        return idf;
    }

    public void setIdf(Idf idf) {
        this.idf = idf;
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
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
}
