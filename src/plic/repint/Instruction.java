package plic.repint;

import plic.exceptions.ErreurSemantique;

/**
 * @author unshade
 */
public abstract class Instruction {
    public abstract void verifier() throws ErreurSemantique;

    public abstract String toMips();
}
