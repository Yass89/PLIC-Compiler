package plic.repint;

import plic.exception.SemanticException;

public abstract class Instruction {
    @Override
    public String toString() {
        return super.toString();
    }

    public abstract void verifier() throws SemanticException;

    public abstract String toMips();

    public abstract String getType();

}
