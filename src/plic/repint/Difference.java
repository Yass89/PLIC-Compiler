package plic.repint;

public class Difference extends Operation {

	public Difference(Operande opUn, Operande opDeux) {
		super(opUn, opDeux, "-");
	}

	@Override
	public String toMips() {
		String mips = super.toMips();

		mips += "# Calcul et stockage du r�sultat dans $v0\n\t";
		mips += "sub $v0, $v1, $v0";

		return mips;
	}

	@Override
	public String getType() {
		return "entier";
	}

}
