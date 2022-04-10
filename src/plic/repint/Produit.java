package plic.repint;

public class Produit extends Operation {

	public Produit(Operande opUn, Operande opDeux) {
		super(opUn, opDeux, "*");
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
