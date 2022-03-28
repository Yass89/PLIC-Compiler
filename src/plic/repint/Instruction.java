package plic.repint;

import plic.exceptions.ErreurSemantique;

public abstract class Instruction {

    public abstract void verifier() throws ErreurSemantique;

    public abstract String toMips();
}
