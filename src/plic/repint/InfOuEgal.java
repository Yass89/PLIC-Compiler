package plic.repint;

public class InfOuEgal extends Operation {

	public InfOuEgal(Operande opUn, Operande opDeux) {
		super(opUn, opDeux, "<=");
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
