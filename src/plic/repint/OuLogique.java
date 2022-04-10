package plic.repint;

public class OuLogique extends Operation {

	public OuLogique(Operande opUn, Operande opDeux) {
		super(opUn, opDeux, "ou");
	}

	@Override
	public String toMips() {
		String mips = super.toMips();

		// Test avec l'operateur ou
		mips += "# Test et stockage du resultat dans $v0\n\t";
		mips += "or $v0, $v0, $v1";

		return mips;
	}

	@Override
	public String getType() {
		return "booleen";
	}

}
