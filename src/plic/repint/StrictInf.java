package plic.repint;

public class StrictInf extends Operation {

	public StrictInf(Operande opUn, Operande opDeux) {
		super(opUn, opDeux, "<");
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
