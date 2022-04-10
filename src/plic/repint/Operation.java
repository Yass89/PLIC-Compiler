package plic.repint;

import java.util.regex.Pattern;

import plic.exceptions.ErreurSemantique;

public abstract class Operation extends Expression {

	protected Operande operandeUn, operandeDeux;
	protected String operateur;

	protected Operation(Operande opUn, Operande opDeux, String operateur) {
		this.operandeUn = opUn;
		this.operandeDeux = opDeux;
		this.operateur = operateur;
	}

	@Override
	public String toString() {
		return operandeUn + " " + operateur + " " + operandeDeux;
	}

	@Override
	public void verifier() throws ErreurSemantique {
		String typeOperandeUn = operandeUn.getType();
		String typeOperandeDeux = operandeDeux.getType();

		// Test de l'operateur
		if (!(Pattern.matches("[-|+|*|et|ou|<|>|=|#|<=|>=]+", operateur)))
			throw new ErreurSemantique("opérandes arithmétiques/relationnels attendus");

		// Test du type des operandes
		// 1er cas (operateur +, -, *,<,>,=,#,<=,>=) : operandes entier
		if (Pattern.matches("[-|+|*|<|>|=|#|<=|>=]+", operateur)
				&& !(typeOperandeUn.equals("entier") && typeOperandeDeux.equals("entier")))
			throw new ErreurSemantique("type incorrect : opérandes entier attendus");

		// 2e cas (operateur ou, et , non) : operandes booleen
		if (Pattern.matches("[et|ou|non]+", operateur)
				&& !(typeOperandeUn.equals("booleen") && typeOperandeDeux.equals("booleen")))
			throw new ErreurSemantique("type incorrect : opérandes booleen attendus");

	}

	@Override
	public String toMips() {
		// Calcul de operandeUn dans $v0
		String mips = operandeUn.toMips();
		return mips;
	}
}
