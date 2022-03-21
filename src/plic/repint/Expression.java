package plic.repint;

import plic.exceptions.ErreurSemantique;

public abstract class Expression {

    public abstract void verifier() throws ErreurSemantique;

    public abstract Object getVal();
}
