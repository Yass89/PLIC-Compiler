package plic.repint;

import plic.exceptions.ErreurSemantique;

/**
 * @author unshade
 */
public abstract class Expression {
    public abstract void verifier() throws ErreurSemantique;

    public abstract Object getVal();
}
