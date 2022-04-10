package plic.repint;

public class Somme extends Operation {

	public Somme(Operande opUn, Operande opDeux) {
		super(opUn, opDeux, "+");
	}

	@Override
	public String toMips() {
		return null;
	}

	@Override
	public String getType() {
		return "entier";
	}

}
