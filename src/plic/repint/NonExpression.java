package plic.repint;

import plic.exceptions.ErreurSemantique;

public class NonExpression extends Operande {

	private Expression expr;

	public NonExpression(Expression expr) {
		this.expr = expr;
	}

	@Override
	public String toString() {
		return "non " + expr;
	}

	@Override
	public void verifier() throws ErreurSemantique {
		if (!expr.getType().equals("booleen"))
			throw new ErreurSemantique("type incorrect : expression booleenne attendue");
	}

	@Override
	public String toMips() {
		return null;
	}

	@Override
	public String getType() {
		return "booleen";
	}

}
