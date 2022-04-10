package plic.repint;

public class StrictSup extends Operation {

	public StrictSup(Operande opUn, Operande opDeux) {
		super(opUn, opDeux, ">");
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
