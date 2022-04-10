package plic.repint;

public class SupOuEgal extends Operation {

	public SupOuEgal(Operande opUn, Operande opDeux) {
		super(opUn, opDeux, ">=");
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
