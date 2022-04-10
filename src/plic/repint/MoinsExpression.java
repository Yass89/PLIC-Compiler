package plic.repint;

import plic.exceptions.ErreurSemantique;

public class MoinsExpression extends Operande {

	private Expression expr;

	public MoinsExpression(Expression expr) {
		this.expr = expr;
	}

	@Override
	public String toString() {
		return "- ( " + expr + " )";
	}

	@Override
	public void verifier() throws ErreurSemantique {
		if (!expr.getType().equals("entier"))
			throw new ErreurSemantique("type incorect : expression enti�re attendue");
	}

	@Override
	public String toMips() {

		String mips = expr.toMips();

		// Multiplication par -1
		mips += "\n\tli $v1, -1\n\tmult $v0, $v1\n\tmflo $v0";
		return mips;
	}

	@Override
	public String getType() {
		return "entier";
	}

}
