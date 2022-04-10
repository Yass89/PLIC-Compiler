package plic.repint;

public class Different extends Operation {

	public Different(Operande opUn, Operande opDeux) {
		super(opUn, opDeux, "#");
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
