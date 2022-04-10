package plic.repint;

import plic.exceptions.ErreurSemantique;

public abstract class Expression {

	public abstract String toString();

	public abstract void verifier() throws ErreurSemantique;

	public abstract String toMips();

	public abstract String getType();
}
