package plic.repint;

import plic.exception.SemanticException;

public abstract class Expression {

    protected String neg = "";

    @Override
    public String toString() {
        return super.toString();
    }

    public abstract void verifier() throws SemanticException;

    public abstract String toMipsLHS();

    public abstract String toMipsRHS();

    public abstract String valeur();

    public abstract String getType();

    public abstract String getAddress();

    public void setNeg(String neg) {
        this.neg=neg;
    }

    public String getNeg() {
        return neg;
    }

    public abstract String inverser();
}
