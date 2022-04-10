package plic.repint;

public class Egalite extends Operation {

	public Egalite(Operande opUn, Operande opDeux) {
		super(opUn, opDeux, "=");
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
