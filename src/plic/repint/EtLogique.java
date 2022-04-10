package plic.repint;

public class EtLogique extends Operation {

	public EtLogique(Operande opUn, Operande opDeux) {
		super(opUn, opDeux, "et");
	}

	@Override
	public String toMips() {
		String mips = super.toMips();

		mips += "# Test et stockage du r�sultat dans $v0\n\t";
		mips += "and $v0, $v0, $v1";

		return mips;
	}

	@Override
	public String getType() {
		return "booleen";
	}

}
