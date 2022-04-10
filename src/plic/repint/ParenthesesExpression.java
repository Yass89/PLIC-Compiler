package plic.repint;

import plic.exceptions.ErreurSemantique;

public class ParenthesesExpression extends Operande {

	private Expression expr;

	public ParenthesesExpression(Expression expr) {
		this.expr = expr;
	}

	@Override
	public String toString() {
		return "( " + expr + " )";
	}

	@Override
	public void verifier() throws ErreurSemantique {
		if (!(expr.getType().equals("entier") || expr.getType().equals("booleen")))
			throw new ErreurSemantique("type incorrect : expression entiere ou booleenne attendue");
	}

	@Override
	public String toMips() {
		return expr.toMips();
	}

	@Override
	public String getType() {
		return expr.getType();
	}

}
